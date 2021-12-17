package com.geek.gmq.producer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author itguoy
 * @date 2021-12-16 15:25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gmq.broker")
public class BrokerConfig {

    private String url = "http://localhost:8080";


}
