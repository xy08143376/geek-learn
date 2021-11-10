package com.geek.shardingsphereproxydb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.geek.shardingsphereproxydb.mapper")
public class ShardingsphereProxyDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereProxyDbApplication.class, args);
    }

}
