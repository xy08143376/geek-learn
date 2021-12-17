package com.geek.gmq.common;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author itguoy
 * @date 2021-12-16 14:39
 */
public final class Gmq<T> {
    private String topic;

    private int capacity;

    private GmqMessage[] elements;

    private AtomicInteger count = new AtomicInteger(0);

    private Map<String, Integer> offsetMap = new ConcurrentHashMap<>(64);


    public Gmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.elements = new GmqMessage[capacity];
    }

    /**
     * 多个生产者的话，线程是否安全？
     *
     * @param message
     * @return
     */
    public boolean send(GmqMessage message) {
        if (count.incrementAndGet() > capacity) {
            throw new RuntimeException("Topic[" + topic + "] is out of capacity.");
        }
        elements[count.get() - 1] = message;
        return true;
    }

    public GmqMessage poll(String consumerId) {
        Integer offset = offsetMap.get(consumerId);
        if (null == offset || offset < 0) {
            offset = 0;
        }
        return elements[offset];
    }

    public GmqMessage poll(String consumerId, int offset) {
        if (offset > count.get()) {
            return null;
        }
        offsetMap.put(consumerId, offset);
        return elements[offset];
    }

    /**
     * 应该不用考虑线程安全问题吧，不存在同时多个consumerId相同的消费者
     *
     * @param consumerId
     */
    public void incConsumerOffset(String consumerId) {
        Integer offset = offsetMap.get(consumerId);
        if (null == offset || offset < 0) {
            offset = 0;
        } else {
            offset++;
        }
        offsetMap.put(consumerId, offset);
    }

    public Integer getOffset(String consumerId) {
        return offsetMap.get(consumerId);
    }
}
