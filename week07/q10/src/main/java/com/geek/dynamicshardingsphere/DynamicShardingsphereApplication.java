package com.geek.dynamicshardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.geek.dynamicshardingsphere.mapper")
public class DynamicShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicShardingsphereApplication.class, args);
    }

}
