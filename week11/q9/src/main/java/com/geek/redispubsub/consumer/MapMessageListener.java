package com.geek.redispubsub.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author itguoy
 * @date 2021-11-26 11:12
 */
@Component("mapMessageListener")
@Slf4j
public class MapMessageListener implements StreamListener<String, MapRecord<String, String, String>> {

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        log.info("接收到redis消息");
        log.info("message id ==> {}", message.getId());
        log.info("stream ==> {}", message.getStream());
        log.info("body ==> {}", message.getValue());
    }
}
