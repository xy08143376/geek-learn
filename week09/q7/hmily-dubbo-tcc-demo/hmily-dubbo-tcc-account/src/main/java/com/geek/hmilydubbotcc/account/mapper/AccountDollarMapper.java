package com.geek.hmilydubbotcc.account.mapper;

import com.geek.hmilydubbotcc.api.entity.AccountDollar;

public interface AccountDollarMapper {
    int deleteByPrimaryKey(Integer accountId);

    int insert(AccountDollar record);

    int insertSelective(AccountDollar record);

    AccountDollar selectByPrimaryKey(Integer accountId);

    int updateByPrimaryKeySelective(AccountDollar record);

    int updateByPrimaryKey(AccountDollar record);
}