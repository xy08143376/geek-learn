package com.geek.redispubsub.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.geek.redispubsub.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author itguoy
 * @date 2021-11-25 15:47
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    RedisTemplate redisTemplate;

    private static final AtomicInteger orderIdCounter = new AtomicInteger(0);

    private static final Random RANDOM = new Random();


    @GetMapping("/pub")
    public String testPub() {
        Order order = new Order();
        order.setOrderId(orderIdCounter.incrementAndGet());
        order.setProdId(RANDOM.nextInt(100));
        order.setProdNum(1);
        order.setOrderAmount(RANDOM.nextInt(100));
        order.setOrderTime(new Date());

        String channel = "orderTopic";
//        redisTemplate.convertAndSend(channel, order);
//        log.info("发送消息成功，{}", order);

        Map<String, Object> map = BeanUtil.beanToMap(order);

        Map<String, String> strMap = new HashMap<>();
        strMap.put("name", "hhhh");

        MapRecord<String, String, String> mapRecord = StreamRecords.string(strMap).withStreamKey("strStream");
        RecordId rId = redisTemplate.opsForStream().add(mapRecord);
        log.info("stream消息发送map成功，{}", rId);

        ObjectRecord<String, Order> objectRecord = StreamRecords.objectBacked(order)
                .withStreamKey("orderObject");
        RecordId recordId = redisTemplate.opsForStream().add(objectRecord);
        log.info("stream 发送object成功，{}", recordId);


//        ObjectRecord<String, Order> record = StreamRecords
//                .objectBacked(order).withStreamKey("mygroup");
//        RecordId recordId = redisTemplate.opsForStream().add(record);


        return "success";
    }


}
