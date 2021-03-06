package com.geek.dynamicds.mapper;

import com.geek.dynamicds.entity.User;

public interface UserMapper {


    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}