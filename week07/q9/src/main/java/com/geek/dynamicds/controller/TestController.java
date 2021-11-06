package com.geek.dynamicds.controller;

import cn.hutool.core.lang.UUID;
import com.geek.dynamicds.config.TargetDataSource;
import com.geek.dynamicds.entity.User;
import com.geek.dynamicds.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itguoy
 * @date 2021-11-06 19:40
 */
@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/master")
    public User testMaster() {
        User user = new User();
        user.setUsername(UUID.fastUUID().toString().substring(10));
        user.setPhone("12345678901");
        user.setPwd("123456");

        userMapper.insert(user);
        return user;
    }

    @GetMapping("/slave/{uId}")
    @TargetDataSource(name = "ds1")
    public User testSlave(@PathVariable("uId") long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

}
