package com.geek.gmq.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author itguoy
 * @date 2021-12-16 17:14
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gmq.consumer")
public class ConsumerConfig {

    private String consumerId = "defaultConsumer";

    private String topic = "defaultTopic";
}
