package com.geek.hmilydubbotcc.account.mapper;

import com.geek.hmilydubbotcc.api.entity.TryLog;

public interface TryLogMapper {
    int deleteByPrimaryKey(String txNo);

    int insert(TryLog record);

    int insertSelective(TryLog record);

    TryLog selectByPrimaryKey(String txNo);

    int updateByPrimaryKeySelective(TryLog record);

    int updateByPrimaryKey(TryLog record);
}