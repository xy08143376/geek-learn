package com.geek.activemqbootdemo.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author itguoy
 * @date 2021-12-03 16:27
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("boot.queue");
    }

    @Bean
    public ActiveMQTopic activeMQTopic() {
        return new ActiveMQTopic("boot.topic");
    }

}
