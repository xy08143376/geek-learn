package com.geek.dynamicshardingsphere;

import cn.hutool.core.lang.UUID;
import com.geek.dynamicshardingsphere.entity.User;
import com.geek.dynamicshardingsphere.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author itguoy
 * @date 2021-11-06 22:05
 */
@RestController
public class TestController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/master")
    public User testMaster() {
        User user = new User();
        user.setUsername(UUID.fastUUID().toString().substring(10));
        user.setPhone("12345678890");
        user.setPwd("123456");
        userMapper.insert(user);
        return user;

    }

    @GetMapping("/slave/{uId}")
    public User testSlave(@PathVariable("uId") long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }
}
