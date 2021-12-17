package com.geek.gmq.broker.service;

import com.geek.gmq.common.Gmq;
import com.geek.gmq.common.GmqMessage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author itguoy
 * @date 2021-12-16 16:12
 */
@Service
public class BrokerService {

    private final Map<String, Gmq> gmqMap = new ConcurrentHashMap<>(64);

    private static final int CAPACITY = 10000;

    public void createTopic(String topic) {
        gmqMap.putIfAbsent(topic, new Gmq(topic, CAPACITY));
    }

    public Gmq findGmq(String topic) {
        return gmqMap.get(topic);
    }




}
