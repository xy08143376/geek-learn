package com.geek.gmq.demo;

import com.geek.gmq.core.GmqBroker;
import com.geek.gmq.core.GmqConsumer;
import com.geek.gmq.core.GmqMessage;
import com.geek.gmq.core.GmqProducer;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author itguoy
 * @date 2021-12-15 15:10
 */
public class GmqDemo {

    @SneakyThrows
    public static void main(String[] args) {
        String topic = "g.test";
        GmqBroker broker = new GmqBroker();
        broker.createTopic(topic);

        GmqConsumer consumer = broker.createConsumer();
        consumer.subscribe(topic);

        final boolean[] flag = new boolean[1];
        flag[0] = true;

        new Thread(() -> {
            while (flag[0]) {
                GmqMessage<Order> message = consumer.poll(100);
                if (null != message) {
                    System.out.println(message.getBody());
                }
            }
            System.out.println("consumer退出");
        }).start();


        GmqProducer producer = broker.createProducer();
        for (int i = 0; i < 1000; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            producer.send(topic, new GmqMessage(null, order));
        }


        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e,退出程序");
        while (true) {
            char c = (char) System.in.read();
            if (c > 20) {
                System.out.println(c);
                producer.send(topic, new GmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.51d)));
            }
            if (c == 'q' || c == 'e') {
                break;
            }
        }

        flag[0] = false;
    }

}
