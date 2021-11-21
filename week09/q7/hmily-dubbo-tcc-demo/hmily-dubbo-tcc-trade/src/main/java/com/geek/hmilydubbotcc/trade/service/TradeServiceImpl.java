package com.geek.hmilydubbotcc.trade.service;

import com.geek.hmilydubbotcc.api.constant.AccountEnum;
import com.geek.hmilydubbotcc.api.entity.AccountCny;
import com.geek.hmilydubbotcc.api.entity.AccountDollar;
import com.geek.hmilydubbotcc.api.entity.LockMoney;
import com.geek.hmilydubbotcc.api.entity.TryLog;
import com.geek.hmilydubbotcc.api.service.AccountService;
import com.geek.hmilydubbotcc.trade.mapper.CancelLogMapper;
import com.geek.hmilydubbotcc.trade.mapper.ConfirmLogMapper;
import com.geek.hmilydubbotcc.trade.mapper.LockMoneyMapper;
import com.geek.hmilydubbotcc.trade.mapper.TryLogMapper;
import org.apache.dubbo.config.annotation.DubboReference;
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

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean trade(String txId, AccountCny fromAccount, AccountDollar toAccount, double amount) {
        // 1. check account exist
        boolean fromExist = accountService.checkAccountExist(fromAccount.getAccountId(), AccountEnum.CNY.getType());
        if (!fromExist) {
            return false;
        }
        boolean toExist = accountService.checkAccountExist(toAccount.getAccountId(), AccountEnum.DOLLAR.getType());
        if (!toExist) {
            return false;
        }

        // 2. checkMoney enough
        boolean fromEnough = accountService.checkAccountAmount(fromAccount.getAccountId(), AccountEnum.CNY.getType(), amount);
        if (!fromEnough) {
            return false;
        }

        // 幂等判断，判断try_log里面是否有try日志记录
        int row = 0;
        if (tryLogMapper.selectByPrimaryKey(txId) != null) {
            // try log 里面存在数据，则直接执行confirm，不再执行后续步骤
            return true;
        }

        // 悬挂处理，如果cancel\confirm有一个已经执行，try不再执行
        if (confirmLogMapper.selectByPrimaryKey(txId) != null || cancelLogMapper.selectByPrimaryKey(txId) != null) {
            return true;
        }


        // 3. lock money
        // 3.1 sub money from fromAccount
        boolean subRes = accountService.subMoney(fromAccount.getAccountId(), AccountEnum.CNY.getType(), amount);
        if (subRes) {
            // 插入try操作日志
            TryLog tryLog = new TryLog();
            tryLog.setTxNo(txId);
            tryLogMapper.insert(tryLog);
        } else {
            throw new HmilyRuntimeException("账户扣减异常");
        }

        // 3.2 write record to lockMoney table
        LockMoney lockMoney = new LockMoney();
        lockMoney.setFromAccountId(fromAccount.getAccountId());
        lockMoney.setAmount(amount);
        row = lockMoneyMapper.insert(lockMoney);




        int a = 10 / 0;

        // 4. add money to to toAccount
        if (row == 0) {
            return false;
        }
        boolean b = accountService.addMoney(toAccount.getAccountId(), AccountEnum.DOLLAR.getType(), amount / 7);
        if (!b) {
            return false;
        }
        // 5. delete lockMoney record from lockMoney table
        row = lockMoneyMapper.deleteByPrimaryKey(lockMoney.getId());
        if (row == 0) {
            return false;
        }

        // success
        return true;
    }


    public String confirm(AccountCny fromAccount, AccountDollar toAccount, double amount) {
        System.out.println("===> 执行confirm方法");
        return "success";
    }

    public String cancel(AccountCny fromAccount, AccountDollar toAccount, double amount) {
        System.out.println("===> 执行cancel方法");
        return "fail";
    }

}
