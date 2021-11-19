package com.geek.rpcfx.demo.provider;

import com.geek.rpcfx.demo.api.User;
import com.geek.rpcfx.demo.api.UserService;

/**
 * @author itguoy
 * @date 2021-11-16 13:45
 */
public class UserServiceImpl implements UserService {


    @Override
    public User findById(int id) {
        return new User(id, "geek-" + System.currentTimeMillis());
    }
}
