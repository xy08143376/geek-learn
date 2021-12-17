package com.geek.gmq.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author itguoy
 * @date 2021-12-15 14:58
 */
public final class GmqBroker {

    public static final int CAPACITY = 10000;

    private final Map<String, Gmq> gmqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        gmqMap.putIfAbsent(name, new Gmq(name, CAPACITY));
    }

    public Gmq findGmq(String topic) {
        return this.gmqMap.get(topic);
    }

    public GmqProducer createProducer() {
        return new GmqProducer(this);
    }

    public GmqConsumer createConsumer() {
        return new GmqConsumer(this);
    }


}
