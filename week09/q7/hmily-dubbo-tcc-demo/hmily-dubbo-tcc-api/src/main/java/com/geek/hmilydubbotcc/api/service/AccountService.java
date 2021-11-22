package com.geek.hmilydubbotcc.api.service;

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
    boolean addMoney(String txId, int accountId, int accountType, double amount);

    @Hmily
    boolean subMoney(String txId, int accountId, int accountType, double amount);


}
