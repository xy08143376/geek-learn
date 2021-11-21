package com.geek.hmilydubbotcc.api.service;

import com.geek.hmilydubbotcc.api.entity.AccountCny;
import com.geek.hmilydubbotcc.api.entity.AccountDollar;
import org.dromara.hmily.annotation.Hmily;

/**
 * @author itguoy
 * @date 2021-11-20 15:41
 */
public interface AccountService {

    @Hmily
    boolean checkAccountExist(int accountId, int accountType);

    @Hmily
    boolean checkAccountAmount(int accountId, int accountType, double money);

    @Hmily
    boolean addMoney(int accountId, int accountType, double amount);

    @Hmily
    boolean subMoney(int accountId, int accountType, double amount);


}
