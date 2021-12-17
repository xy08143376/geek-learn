package com.geek.gmq.producer;

import com.geek.gmq.common.GmqMessage;
import com.geek.gmq.common.demo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author itguoy
 * @date 2021-12-16 15:57
 */
@SpringBootApplication
public class ProducerApp {

    @Autowired
    GmqProducer gmqProducer;


    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class, args);
    }

    @Bean
    ApplicationRunner run() {
        return (args) -> {

            String topic = "v3.test";
            for (int i = 0; i < 1000; i++) {
                Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
                boolean b = gmqProducer.send(topic, new GmqMessage(null, order));
                if (b) {
                    System.out.println("producer 发送成功");
                } else {
                    System.out.println("producer 发送失败");
                }
            }

            while (true) {
                char ch = (char) System.in.read();
                if (ch > 20) {
                    Order order = new Order(10000L + ch, System.currentTimeMillis(), "USD2CNY", 6.51d);
                    boolean b = gmqProducer.send(topic, new GmqMessage(null, order));
                    if (b) {
                        System.out.println("发送成功");
                    } else {
                        System.out.println("发送失败");
                    }
                }
                if (ch == 'e' || ch == 'q') {
                    break;
                }
            }

        };
    }

}
