package com.geek.activemqbootdemo;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJms
public class ActivemqBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqBootDemoApplication.class, args);
    }

    @Autowired
    ActiveMQQueue queue;

    @Autowired
    ActiveMQTopic topic;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @GetMapping("/queue")
    public String sendQueue() {
        jmsMessagingTemplate.convertAndSend(queue, "hello,queue");
        return "success";
    }

    @GetMapping("/topic")
    public String sendTopic() {
        jmsMessagingTemplate.convertAndSend(topic, "boot.topic");
        return "success";
    }


}
