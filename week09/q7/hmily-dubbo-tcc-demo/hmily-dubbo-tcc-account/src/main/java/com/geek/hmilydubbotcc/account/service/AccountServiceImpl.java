package com.geek.hmilydubbotcc.account.service;

import com.geek.hmilydubbotcc.account.mapper.AccountCnyMapper;
import com.geek.hmilydubbotcc.account.mapper.AccountDollarMapper;
import com.geek.hmilydubbotcc.api.constant.AccountEnum;
import com.geek.hmilydubbotcc.api.entity.AccountCny;
import com.geek.hmilydubbotcc.api.entity.AccountDollar;
import com.geek.hmilydubbotcc.api.service.AccountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author itguoy
 * @date 2021-11-20 15:45
 */

@DubboService(version = "1.0.0")
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountCnyMapper accountCnyMapper;

    @Autowired
    AccountDollarMapper accountDollarMapper;

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

//    @HmilyTCC(confirmMethod = "addMoneyConfirm", cancelMethod = "addMoneyCancel")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMoney(int accountId, int accountType, double amount) {
        System.out.println("---> add money");
        int row = 0;
        if (accountType == AccountEnum.CNY.getType()) {
            AccountCny accountCny = accountCnyMapper.selectByPrimaryKey(accountId);
            accountCny.setAmount(accountCny.getAmount() + amount);
            row = accountCnyMapper.updateByPrimaryKeySelective(accountCny);
        } else if (accountType == AccountEnum.DOLLAR.getType()) {
            AccountDollar accountDollar = accountDollarMapper.selectByPrimaryKey(accountId);
            accountDollar.setAmount(accountDollar.getAmount() + amount);
            row = accountDollarMapper.updateByPrimaryKeySelective(accountDollar);
        }
        return row > 0;
    }

    public boolean addMoneyConfirm(int accountId, int accountType, double amount) {
        System.out.println("--> addMoney调用成功");
        return true;
    }

    public boolean addMoneyCancel(int accountId, int accountType, double amount) {
        System.out.println("--> addMoney调用失败");
        return false;
    }

//    @HmilyTCC(confirmMethod = "subMoneyConfirm", cancelMethod = "subMoneyCancel")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subMoney(int accountId, int accountType, double amount) {
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
//        int a = 10 / 0;
        return row > 0;
    }

    public boolean subMoneyConfirm(int accountId, int accountType, double amount) {
        System.out.println("--> subMoney调用成功");
        return true;
    }

    public boolean subMoneyCancel(int accountId, int accountType, double amount) {
        System.out.println("--> subMoney调用失败");
        return false;
    }


}
