package com.geek.rpcfx.demo.provider;

import com.geek.rpcfx.demo.api.Order;
import com.geek.rpcfx.demo.api.OrderService;

/**
 * @author itguoy
 * @date 2021-11-16 13:47
 */
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "geek java " + System.currentTimeMillis(), 3499f);
    }
}
