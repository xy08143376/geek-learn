package com.geek.springkafkacluster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author itguoy
 * @date 2021-12-15 14:06
 */
@RestController
public class TestController {

    private static final String topic = "testTopic";

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public String send() {
        String uuid = UUID.randomUUID().toString();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, "key", uuid);
        try {
            SendResult<String, String> sendResult = future.get();
            String ret = sendResult.toString();
            System.out.println("发送成功 ==> " + ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return uuid;
    }

}
