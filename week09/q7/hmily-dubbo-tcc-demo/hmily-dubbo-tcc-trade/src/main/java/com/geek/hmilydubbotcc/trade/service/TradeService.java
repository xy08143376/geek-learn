package com.geek.hmilydubbotcc.trade.service;

import com.geek.hmilydubbotcc.api.entity.AccountCny;
import com.geek.hmilydubbotcc.api.entity.AccountDollar;

/**
 * @author itguoy
 * @date 2021-11-20 16:10
 */
public interface TradeService {

    boolean trade(String txId, AccountCny from, AccountDollar to, double amount);


}
