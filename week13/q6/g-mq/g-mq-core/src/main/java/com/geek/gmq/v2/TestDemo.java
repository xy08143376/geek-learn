package com.geek.gmq.v2;

import com.geek.gmq.core.GmqMessage;
import com.geek.gmq.demo.Order;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author itguoy
 * @date 2021-12-16 11:07
 */
public class TestDemo {

    @SneakyThrows
    public static void main(String[] args) {
        String topic = "gv2.test";
        GMqBroker broker = new GMqBroker();
        broker.createTopic(topic);

        String consumer1Id = "consumer1";
        GMqConsumer consumer1 = broker.createConsumer(consumer1Id);
        String consumer2Id = "consumer2";
        GMqConsumer consumer2 = broker.createConsumer(consumer2Id);

        consumer1.subscribe(topic);
        consumer2.subscribe(topic);

        final boolean[] flag = new boolean[2];
        flag[0] = true;
        flag[1] = true;

        // 启动consumer1
        new Thread(() -> {
            while (flag[0]) {
                GmqMessage<Order> message = consumer1.poll();
                if (message != null) {
                    Integer offset = consumer1.getOffset();
                    System.out.println("consumer1 消费到第[" + offset + "]消息 ==> " + message.getBody());
                    consumer1.ack();
                    try {
                        TimeUnit.MILLISECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("consumer1 退出");
        }).start();

        // 启动consumer2
        new Thread(() -> {
            while (flag[1]) {
                GmqMessage<Order> message = consumer2.poll();
                if (message != null) {
                    Integer offset = consumer2.getOffset();
                    System.out.println("consumer2 消费到第[" + offset + "]消息 ==> " + message.getBody());
                    consumer2.ack();
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 启动生产者
        GMqProducer producer = broker.createProducer();
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
        flag[1] = false;

    }

}
