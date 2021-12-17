package com.geek.gmq.v2;

import cn.hutool.core.lang.UUID;
import com.geek.gmq.core.GmqMessage;

/**
 * @author itguoy
 * @date 2021-12-16 10:54
 */
public class GMqConsumer<T> {

    private final GMqBroker broker;

    private GMq gMq;

    private String consumerId;

    public GMqConsumer(GMqBroker broker) {
        this.broker = broker;
        this.consumerId = UUID.randomUUID().toString();
    }

    public GMqConsumer(GMqBroker broker, String consumerId) {
        this.broker = broker;
        this.consumerId = consumerId;
    }

    public void subscribe(String topic) {
        this.gMq = broker.findGMq(topic);
        if (gMq == null) {
            throw new RuntimeException("Topic[" + topic + "] dosen't exist.");
        }
    }

    public GmqMessage<T> poll() {
        return gMq.poll(this.consumerId);
    }

    public Integer getOffset() {
        return gMq.getOffset(this.consumerId);
    }

    public void ack() {
        gMq.incConsumerOffset(this.consumerId);
    }
}
