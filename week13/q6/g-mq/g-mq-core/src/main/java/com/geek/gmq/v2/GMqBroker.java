package com.geek.gmq.v2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author itguoy
 * @date 2021-12-16 10:45
 */
public final class GMqBroker {

    public static final int CAPACITY = 10000;

    private final Map<String, GMq> gMqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        gMqMap.putIfAbsent(name, new GMq(name, CAPACITY));
    }

    public GMq findGMq(String topic) {
        return this.gMqMap.get(topic);
    }

    public GMqProducer createProducer() {
        return new GMqProducer(this);
    }

    public GMqConsumer createConsumer(String consumerId) {
        return new GMqConsumer(this, consumerId);
    }


}
