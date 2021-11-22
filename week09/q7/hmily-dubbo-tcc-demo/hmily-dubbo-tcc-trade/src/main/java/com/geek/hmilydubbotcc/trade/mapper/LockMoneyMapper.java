package com.geek.hmilydubbotcc.trade.mapper;
import org.apache.ibatis.annotations.Param;

import com.geek.hmilydubbotcc.api.entity.LockMoney;

public interface LockMoneyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LockMoney record);

    int insertSelective(LockMoney record);

    LockMoney selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LockMoney record);

    int updateByPrimaryKey(LockMoney record);

    LockMoney selectOneByFromAccountId(@Param("fromAccountId")Integer fromAccountId);

    int deleteByFromAccountId(@Param("fromAccountId")Integer fromAccountId);




}