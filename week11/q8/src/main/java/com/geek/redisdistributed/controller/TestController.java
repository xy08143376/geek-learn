package com.geek.redisdistributed.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author itguoy
 * @date 2021-11-25 9:55
 */
@RestController
@Slf4j
public class TestController {


    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Value("${server.port}")
    int serverPort;

    private static final String LOCK_KEY = "dlock";

    private static final Random RANDOM = new Random();

    /**
     * 测试分布式锁
     *
     * @return
     */
    @GetMapping("/lock")
    public String testDistributeLock() {
        log.info(" {} 机器 {} 线程 开始获取redis锁。。。", serverPort, Thread.currentThread().getName());
        try {
            boolean b = false;
            while (!(b = redisTemplate.opsForValue().setIfAbsent(LOCK_KEY, serverPort, 10, TimeUnit.SECONDS))) {
                // 此处自旋
                log.info("{}机器{}线程 自旋尝试获取redis锁。。。", serverPort, Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            }
            // 获取到锁

            log.info("{} 机器{}线程 获取redis锁成功,开始执行业务。。。", serverPort, Thread.currentThread().getName());
            int time = RANDOM.nextInt(10) + 5;
            TimeUnit.SECONDS.sleep(time);
            log.info("{}机器{}线程 执行业务完成，耗时：{}s", serverPort, Thread.currentThread().getName(), time);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
            redisScript.setResultType(Long.class);
            redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/redis_unlock.lua")));
            Long result = redisTemplate.execute(redisScript, Arrays.asList(LOCK_KEY));
            if (result == 1) {
                log.info("{} 机器 {} 线程释放redis锁成功。", serverPort, Thread.currentThread().getName());
            }
        }


        return "success";
    }

    private static final String COUNT_KEY = "dcount";

    /**
     * 初始化库存数据
     *
     * @return
     */
    @GetMapping("/init")
    public String initStore() {
        redisTemplate.opsForValue().set(COUNT_KEY, 100, 10, TimeUnit.MINUTES);
        return "success";
    }


    /**
     * 测试分布式计数器，模拟减库存
     *
     * @return
     */
    @GetMapping("/counter")
    public String testDistributeCounter() {
        Long storeCount = redisTemplate.opsForValue().decrement(COUNT_KEY);
        return "store is " + storeCount;
    }


}
