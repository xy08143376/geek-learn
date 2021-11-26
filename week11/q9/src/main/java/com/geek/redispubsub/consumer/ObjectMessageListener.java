package com.geek.redispubsub.consumer;

import com.geek.redispubsub.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author itguoy
 * @date 2021-11-25 16:04
 */
@Component("objectMessageListener")
@Slf4j
public class ObjectMessageListener implements StreamListener<String, ObjectRecord<String, Order>> {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, Order> message) {
        try {
            log.info("receive redis key ==> {} ", message.getStream());
            log.info("receive the recordId ==> {}", message.getId());
            Order value = message.getValue();
            log.info("receive the order is ==> {}", value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisTemplate.opsForStream().acknowledge("objectGroup", message);
        }
    }
}
