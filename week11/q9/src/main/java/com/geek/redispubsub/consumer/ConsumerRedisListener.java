package com.geek.redispubsub.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author itguoy
 * @date 2021-11-26 10:39
 */
@Component
@Slf4j
public class ConsumerRedisListener implements MessageListener {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Object value = redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.info("收到消息 ==> {}", value);
    }
}
