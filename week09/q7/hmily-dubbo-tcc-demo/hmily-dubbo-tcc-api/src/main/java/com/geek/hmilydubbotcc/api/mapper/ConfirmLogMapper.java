package com.geek.hmilydubbotcc.api.mapper;

import com.geek.hmilydubbotcc.api.entity.ConfirmLog;

public interface ConfirmLogMapper {
    int deleteByPrimaryKey(String txNo);

    int insert(ConfirmLog record);

    int insertSelective(ConfirmLog record);

    ConfirmLog selectByPrimaryKey(String txNo);

    int updateByPrimaryKeySelective(ConfirmLog record);

    int updateByPrimaryKey(ConfirmLog record);
}