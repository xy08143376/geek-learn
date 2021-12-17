package com.geek.gmq.core;

/**
 * @author itguoy
 * @date 2021-12-15 15:03
 */
public class GmqProducer {

    private GmqBroker broker;

    public GmqProducer(GmqBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, GmqMessage message) {
        Gmq gmq = this.broker.findGmq(topic);
        if (null == gmq) {
            throw new RuntimeException("Topic[" + topic + "] dosen't exist.");
        }
        return gmq.send(message);
    }
}
