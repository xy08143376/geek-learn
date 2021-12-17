package com.geek.gmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author itguoy
 * @date 2021-12-15 14:54
 */
public final class Gmq {

    private String topic;

    private int capacity;

    private LinkedBlockingQueue<GmqMessage> queue;


    /**
     * @param topic
     * @param capacity
     */
    public Gmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    public boolean send(GmqMessage message) {
        return queue.offer(message);
    }

    public GmqMessage poll() {
        return queue.poll();
    }

    @SneakyThrows
    public GmqMessage poll(long timeout) {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }
}
