package com.geek.gmq.core;

/**
 * @author itguoy
 * @date 2021-12-15 15:06
 */
public class GmqConsumer<T> {

    private final GmqBroker broker;

    private Gmq gmq;

    public GmqConsumer(GmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.gmq = this.broker.findGmq(topic);
        if (gmq == null) {
            throw new RuntimeException("Topic[" + topic + "] dosen't exist.");
        }
    }

    public GmqMessage<T> poll(long timeout) {
        return gmq.poll(timeout);
    }
}
