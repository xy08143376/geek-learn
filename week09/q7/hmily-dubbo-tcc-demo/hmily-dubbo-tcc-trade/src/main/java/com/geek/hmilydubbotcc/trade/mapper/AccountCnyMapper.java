package com.geek.hmilydubbotcc.trade.mapper;

import com.geek.hmilydubbotcc.api.entity.AccountCny;

public interface AccountCnyMapper {
    int deleteByPrimaryKey(Integer accountId);

    int insert(AccountCny record);

    int insertSelective(AccountCny record);

    AccountCny selectByPrimaryKey(Integer accountId);

    int updateByPrimaryKeySelective(AccountCny record);

    int updateByPrimaryKey(AccountCny record);
}