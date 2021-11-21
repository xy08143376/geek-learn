package com.geek.hmilydubbotcc.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author itguoy
 * @date 2021-11-20 15:22
 */

@SpringBootApplication
@MapperScan("com.geek.hmilydubbotcc.account.mapper")
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
