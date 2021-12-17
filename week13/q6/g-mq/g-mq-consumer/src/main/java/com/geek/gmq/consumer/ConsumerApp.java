package com.geek.gmq.consumer;

import com.alibaba.fastjson.JSON;
import com.geek.gmq.common.GmqMessage;
import com.geek.gmq.common.demo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

/**
 * @author itguoy
 * @date 2021-12-16 17:04
 */
@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @Autowired
    GmqConsumer consumer;

    @Bean
    ApplicationRunner runner() {
        return args -> {
            new Thread(() -> {
                int offset = new Random().nextInt(1000);
                boolean offsetConsume = true;
                while (true) {
                    GmqMessage<Order> message = null;
                    if (offsetConsume) {
                        message = consumer.poll(Order.class, offset);
                    } else {
                        message = consumer.poll(Order.class);
                    }
                    if (message != null) {
                        offsetConsume = false;
                        Order order = message.getBody();
                        System.out.println("消费者消费消息 ==> " + JSON.toJSONString(order));
                        consumer.ack();
                    }
                }
            }).start();
        };
    }

}
