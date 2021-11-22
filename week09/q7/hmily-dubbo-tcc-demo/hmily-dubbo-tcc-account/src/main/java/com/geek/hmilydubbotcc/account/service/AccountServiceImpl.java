package com.geek.hmilydubbotcc.account.service;

import com.geek.hmilydubbotcc.account.mapper.*;
import com.geek.hmilydubbotcc.api.constant.AccountEnum;
import com.geek.hmilydubbotcc.api.entity.*;
import com.geek.hmilydubbotcc.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

/**
 * @author itguoy
 * @date 2021-11-20 15:45
 */
@Slf4j
@DubboService(version = "1.0.0")
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountCnyMapper accountCnyMapper;

    @Autowired
    AccountDollarMapper accountDollarMapper;

    @Autowired
    TryLogMapper tryLogMapper;

    @Autowired
    ConfirmLogMapper confirmLogMapper;

    @Autowired
    CancelLogMapper cancelLogMapper;

    @Override
    public boolean checkAccountExist(int accountId, int accountType) {
        System.out.println("---> check account exist");
        if (accountType == AccountEnum.CNY.getType()) {
            AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(accountId);
            return accountCny != null;
        } else if (accountType == AccountEnum.DOLLAR.getType()) {
            AccountDollar accountDollar = accountDollarMapper.selectByPrimaryKey(accountId);
            return accountDollar != null;
        }
        return false;
    }

    @Override
    public boolean checkAccountAmount(int accountId, int accountType, double money) {
        System.out.println("---> check account money");
        if (accountType == AccountEnum.CNY.getType()) {
            // cny account
            AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(accountId);
            return accountCny.getAmount() >= money;
        } else if (accountType == AccountEnum.DOLLAR.getType()) {
            // dollar account
            AccountDollar accountDollar = accountDollarMapper.selectByPrimaryKey(accountId);
            return accountDollar.getAmount() >= money;
        }
        return false;
    }

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    public boolean addMoney(String txId, int accountId, int accountType, double amount) {
        log.info("add money try begin...");
        // 幂等校验
        if (tryLogMapper.selectByPrimaryKey(txId) == null) {
            TryLog tryLog = new TryLog();
            tryLog.setTxNo(txId);
            tryLogMapper.insert(tryLog);
        }
        log.info("add money try end...");
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(String txId, int accountId, int accountType, double amount) {
        log.info("add money confirm begin...");
        // 幂等校验，且try执行完成
        if (confirmLogMapper.selectByPrimaryKey(txId) == null) {
            if (tryLogMapper.selectByPrimaryKey(txId) != null) {
                if (accountType == AccountEnum.DOLLAR.getType()) {
                    AccountDollar accountDollar = accountDollarMapper.selectByPrimaryKey(accountId);
                    accountDollar.setAmount(accountDollar.getAmount() + (amount / 7));
                    accountDollarMapper.updateByPrimaryKeySelective(accountDollar);
                } else {
                    AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(accountId);
                    accountCny.setAmount(accountCny.getAmount() + amount);
                    accountCnyMapper.updateByPrimaryKeySelective(accountCny);
                }
                ConfirmLog confirmLog = new ConfirmLog();
                confirmLog.setTxNo(txId);
                confirmLogMapper.insert(confirmLog);
            }
        }
        log.info("add money confirm end...");

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(String txId, int accountId, int accountType, double amount) {
        log.info("add money cancel begin...");
        // 幂等校验，且try执行完毕
        if (cancelLogMapper.selectByPrimaryKey(txId) == null) {
            if (tryLogMapper.selectByPrimaryKey(txId) != null) {
                if (accountType == AccountEnum.DOLLAR.getType()) {
                    AccountDollar accountDollar = accountDollarMapper.selectByPrimaryKey(accountId);
                    accountDollar.setAmount(accountDollar.getAmount() - (amount / 7));
                    accountDollarMapper.updateByPrimaryKeySelective(accountDollar);
                } else {
                    AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(accountId);
                    accountCny.setAmount(accountCny.getAmount() - amount);
                    accountCnyMapper.updateByPrimaryKeySelective(accountCny);
                }
                CancelLog cancelLog = new CancelLog();
                cancelLog.setTxNo(txId);
                cancelLogMapper.insert(cancelLog);
            }
        }
        log.info("add money cancel end...");
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subMoney(String txId, int accountId, int accountType, double amount) {
        System.out.println("---> sub money");
        int row = 0;
        if (accountType == AccountEnum.CNY.getType()) {
            AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(accountId);
            accountCny.setAmount(accountCny.getAmount() - amount);
            row = accountCnyMapper.updateByPrimaryKeySelective(accountCny);
        } else if (accountType == AccountEnum.DOLLAR.getType()) {
            AccountDollar accountDollar = accountDollarMapper.selectByPrimaryKey(accountId);
            accountDollar.setAmount(accountDollar.getAmount() - amount);
            row = accountDollarMapper.updateByPrimaryKeySelective(accountDollar);
        }
        return row > 0;
    }


}
