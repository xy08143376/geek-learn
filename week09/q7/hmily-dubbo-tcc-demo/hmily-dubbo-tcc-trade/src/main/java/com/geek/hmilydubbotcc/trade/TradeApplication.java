package com.geek.hmilydubbotcc.trade;

import com.geek.hmilydubbotcc.api.entity.AccountCny;
import com.geek.hmilydubbotcc.api.entity.AccountDollar;
import com.geek.hmilydubbotcc.api.service.AccountService;
import com.geek.hmilydubbotcc.trade.service.TradeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.UUID;

/**
 * @author itguoy
 * @date 2021-11-20 15:28
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.geek.hmilydubbotcc.trade.mapper")
public class TradeApplication {


    @Autowired
    TradeServiceImpl tradeService;

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            AccountCny fromAccount = new AccountCny();
            fromAccount.setAccountId(1);
            fromAccount.setUserId(1);
            fromAccount.setAmount(100d);

            AccountDollar toAccount = new AccountDollar();
            toAccount.setAccountId(2);
            toAccount.setUserId(2);
            toAccount.setAmount(100d);

            String txId = UUID.randomUUID().toString();
            boolean b = tradeService.trade(txId, fromAccount, toAccount, 100);

            log.error("result：{}", b);
        };
    }

}
