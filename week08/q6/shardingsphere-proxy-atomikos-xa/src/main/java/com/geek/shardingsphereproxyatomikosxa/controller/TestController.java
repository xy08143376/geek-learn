package com.geek.shardingsphereproxyatomikosxa.controller;

import com.geek.shardingsphereproxyatomikosxa.entity.TOrder;
import com.geek.shardingsphereproxyatomikosxa.mapper.TOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author itguoy
 * @date 2021-11-11 10:16
 */
@RestController
public class TestController {

    @Autowired
    TOrderMapper orderMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;

    @GetMapping("/order/{orderId}")
    public TOrder getOneById(@PathVariable("orderId") long orderId) {
        return orderMapper.selectById(orderId);
    }

    /**
     * 分布式事务测试，本地服务调用另一个服务
     *
     * @return
     */
    @PostMapping("/order/success/")
    public String insertSuccess() {
        TOrder order = buildOrder();

        // 执行事务，包含本地事务和另一个服务的事务
        Boolean execute = transactionTemplate.execute(status -> {
            // 执行本地事务
            int localRow = orderMapper.insert(order);
            System.out.println("本地插入成功");

            // 调用服务，执行远程事务
            String s = restTemplate.postForObject("http://localhost:8080/order/success/",
                    null, String.class);
            System.out.println(s);
            return s.indexOf("成功") > -1;
        });

        return execute ? "success" : "fail";
    }

    @PostMapping("/order/fail/")
    public String insertFail() {
        TOrder tOrder = buildOrder();

        // 执行事务
        Boolean success = transactionTemplate.execute(status -> {

            // 执行本地事务
            int localRow = orderMapper.insert(tOrder);
            System.out.println("本地插入成功：" + tOrder.toString());

            // 调用服务，执行远程事务
            String s = restTemplate.postForObject("http://localhost:8080/order/fail/", null, String.class);
            System.out.println(s);

            return s.indexOf("成功") > -1;
        });

        return success ? "success" : "fail";

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
