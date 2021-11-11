package com.geek.shardingsphereproxyatomikosxa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.geek.shardingsphereproxyatomikosxa.mapper")
public class ShardingsphereProxyAtomikosXaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereProxyAtomikosXaApplication.class, args);
    }

}
