package com.geek.shardingsphereproxydb.controller;

import com.geek.shardingsphereproxydb.entity.TOrder;
import com.geek.shardingsphereproxydb.mapper.TOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author itguoy
 * @date 2021-11-10 10:20
 */
@RestController
public class OrderController {


    @Autowired
    TOrderMapper orderMapper;

    @Autowired
    TransactionTemplate transactionTemplate;

    @GetMapping("/order/{orderId}")
    public TOrder getById(@PathVariable("orderId") long orderId) {
        return orderMapper.selectById(orderId);
    }

    @PostMapping("/order/success")
    public String insert() {
        TOrder order = buildOrder();
        Integer execute = transactionTemplate.execute((status) -> {
            int row = orderMapper.insert(order);
            return row;
        });
        if (execute > 0) {
            return "155插入成功：" + order.toString();
        } else {
            return "155插入失败：" + order.toString();
        }

    }

    @PostMapping("/order/fail/")
    public String insertFail() {
        TOrder order = buildOrder();
        Boolean execute = transactionTemplate.execute((status) -> {
            orderMapper.insert(order);
            System.out.println(order.toString());
            // 故意抛出异常
            int a = 10 / 0;
            return false;
        });
        String ret = "";
        if (!execute) {
            ret = "155插入失败: " + order.toString();
        } else {
            ret = "155插入成功：" + order.toString();
        }

        return ret;

    }

    private TOrder buildOrder() {
        TOrder order = new TOrder();
        Random random = new Random();
        order.setUserId((long) random.nextInt(1000) + 1);
        double price = (random.nextInt(1000) + 100) / ((random.nextInt(10) + 1) * 1.0);
        order.setOrderPrice(BigDecimal.valueOf(price));
        int num = random.nextInt(10) + 1;
        order.setOrderNum(num);
        order.setOrderTotalPrice(BigDecimal.valueOf(price * num));
        order.setProdId((long) random.nextInt(10000) + 1);
        order.setOrderStatus(random.nextInt(4));
        return order;
    }


}
