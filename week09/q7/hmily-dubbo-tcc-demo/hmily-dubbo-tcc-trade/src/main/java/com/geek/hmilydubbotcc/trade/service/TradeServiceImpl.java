package com.geek.hmilydubbotcc.trade.service;

import com.geek.hmilydubbotcc.api.constant.AccountEnum;
import com.geek.hmilydubbotcc.api.entity.*;
import com.geek.hmilydubbotcc.api.service.AccountService;
import com.geek.hmilydubbotcc.trade.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.zookeeper.Login;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author itguoy
 * @date 2021-11-20 15:51
 */
@Component
@Slf4j
public class TradeServiceImpl implements TradeService {

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private AccountService accountService;

    @Autowired
    LockMoneyMapper lockMoneyMapper;

    @Autowired
    ConfirmLogMapper confirmLogMapper;

    @Autowired
    CancelLogMapper cancelLogMapper;

    @Autowired
    TryLogMapper tryLogMapper;

    @Autowired
    AccountCnyMapper accountCnyMapper;

    /**
     * 分布式事务
     * try阶段：  完成业务检查包括：检查账户A金额是否足够、冻结账户A部分资金，调用远程加钱
     * confirm： 执行业务：释放冻结资金
     * cancel： 取消业务执行： 释放冻结资金，钱加回A账户
     *
     * @param txId
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @return
     */

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    public boolean trade(String txId, AccountCny fromAccount, AccountDollar toAccount, double amount) {
        log.info("from subMoney try begin: txId is {}, fromAccount amount is {}", txId, fromAccount.getAmount());
        // 幂等判断，判断try_log里面是否有try日志记录,如果有，则不再执行
        if (tryLogMapper.selectByPrimaryKey(txId) != null) {
            log.info("txId为 {} 的try操作已执行，直接退出", txId);
            return true;
        }
        // 悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if (confirmLogMapper.selectByPrimaryKey(txId) != null ||
                cancelLogMapper.selectByPrimaryKey(txId) != null) {
            log.info("txId 为 {} 的cancel或者confirm操作已执行，直接退出", txId);
            return true;
        }

        // 检查账户是否存在
        AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(fromAccount.getAccountId());
        if (accountCny == null) {
            return false;
        }
        boolean toExist = accountService.checkAccountExist(toAccount.getAccountId(), AccountEnum.DOLLAR.getType());
        if (!toExist) {
            return false;
        }

        // 检查金额是否充足操作
        if (accountCny.getAmount() < amount) {
            log.error("txId为 {} 的操作执行失败，fromAccount 的余额为 {} ，余额不足扣款，直接退出", txId, fromAccount.getAmount());
            return false;
        }
        // 扣减金额
        accountCny.setAmount(accountCny.getAmount() - amount);
        int row1 = accountCnyMapper.updateByPrimaryKeySelective(accountCny);
        // 冻结金额
        LockMoney lockMoney = new LockMoney();
        lockMoney.setFromAccountId(fromAccount.getAccountId());
        lockMoney.setAmount(amount);
        int row2 = lockMoneyMapper.insert(lockMoney);
        if (row1 > 0 && row2 > 0) {
            log.info("fromAccount 扣减金额成功，并且资金已经冻结，txId为 {}", txId);
            // 插入try操作日志
            TryLog tryLog = new TryLog();
            tryLog.setTxNo(txId);
            tryLogMapper.insert(tryLog);
            log.info("插入try log 成功。。。");
        } else if (row2 == 0) {
            throw new HmilyRuntimeException("账户扣减异常");
        }
        // 调用远程增加金额
        boolean b = accountService.addMoney(txId,toAccount.getAccountId(), AccountEnum.DOLLAR.getType(), amount);
        if (!b) {
            throw new HmilyRuntimeException("远程增加金额异常");
        }

        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(String txId, AccountCny fromAccount, AccountDollar toAccount, double amount) {
        System.out.println("===> 执行confirm方法");
        // 幂等判断，如果confirm操作未执行，才能去加钱，解除冻结金额，否则什么也不做。
        if (confirmLogMapper.selectByPrimaryKey(txId) == null) {
            // 只有try操作完成后，且cancel操作未执行的情况下，才允许执行confirm
            if (tryLogMapper.selectByPrimaryKey(txId) != null && cancelLogMapper.selectByPrimaryKey(txId) == null) {
                // 解冻金额
                int row = lockMoneyMapper.deleteByFromAccountId(fromAccount.getAccountId());
                if (row > 0) {
                    log.info("解冻金额成功");
                    // 写入confirm日志
                    ConfirmLog confirmLog = new ConfirmLog();
                    confirmLog.setTxNo(txId);
                    confirmLogMapper.insert(confirmLog);
                }
            }
        }
        log.info("转账confirm操作end，txId 为 {} ，转账金额为 {}", txId, amount);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(String txId, AccountCny fromAccount, AccountDollar toAccount, double amount) {
        System.out.println("===> 执行cancel方法");
        // 幂等校验，只有当cancel操作未执行的情况下，才执行cancel，否则什么也不做
        if (cancelLogMapper.selectByPrimaryKey(txId) == null) {
            // 空回滚操作，如果try操作未执行，那么cancel什么也不做，当且仅当try执行后，才能执行cancel操作
            if (tryLogMapper.selectByPrimaryKey(txId) != null) {
                // 需要判断confirm是否执行了
                // 如果confirm还未执行，那么需要解冻金额
                if (confirmLogMapper.selectByPrimaryKey(txId) == null) {
                    log.info("cancel操作中，解除冻结金额");
                    lockMoneyMapper.deleteByFromAccountId(fromAccount.getAccountId());
                }
                log.info("解除冻结成功");
                // 增加账户余额
                AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(fromAccount.getAccountId());
                accountCny.setAmount(accountCny.getAmount() + amount);
                accountCnyMapper.updateByPrimaryKeySelective(accountCny);
                log.info("增加账户余额成功");

                CancelLog cancelLog = new CancelLog();
                cancelLog.setTxNo(txId);
                cancelLogMapper.insert(cancelLog);
            }
        }
        log.info("cancel操作end，txId 为 {}", txId);
        return true;
    }

}
