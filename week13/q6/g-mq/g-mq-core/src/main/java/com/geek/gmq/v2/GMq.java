package com.geek.gmq.v2;

import com.geek.gmq.core.GmqMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author itguoy
 * @date 2021-12-15 16:19
 */
public class GMq {

    private String topic;

    private int capacity = 10;

    private GmqMessage[] elements;

    private AtomicInteger count = new AtomicInteger(0);

    private Map<String, Integer> offsetMap = new ConcurrentHashMap<>(64);


    public GMq(String topic, int capacity) {
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
