package com.geek.hmilydubbotcc.trade.mapper;

import com.geek.hmilydubbotcc.api.entity.CancelLog;

public interface CancelLogMapper {
    int deleteByPrimaryKey(String txNo);

    int insert(CancelLog record);

    int insertSelective(CancelLog record);

    CancelLog selectByPrimaryKey(String txNo);

    int updateByPrimaryKeySelective(CancelLog record);

    int updateByPrimaryKey(CancelLog record);
}