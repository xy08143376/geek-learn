package com.geek.hmilydubbotcc.trade.mapper;

import com.geek.hmilydubbotcc.api.entity.LockMoney;

public interface LockMoneyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LockMoney record);

    int insertSelective(LockMoney record);

    LockMoney selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LockMoney record);

    int updateByPrimaryKey(LockMoney record);
}