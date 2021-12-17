package com.geek.springkafkacluster.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author itguoy
 * @date 2021-12-15 14:10
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "testTopic", groupId = "testGroup")
    public void listenerTestGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println("testGroup 接收到testTopic消息 ==> " + value);
        System.out.println(record);
        // 手动确认
        ack.acknowledge();
    }

    @KafkaListener(topics = "testTopic", groupId = "testGroup2")
    public void listenerTestGroup2(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println("testGroup2 接收到testTopic消息 ==> " + value);
        System.out.println(record);
        // 手动确认
        ack.acknowledge();
    }

}
