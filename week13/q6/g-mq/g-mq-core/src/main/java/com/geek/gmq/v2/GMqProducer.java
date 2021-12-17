package com.geek.gmq.v2;

import com.geek.gmq.core.GmqMessage;

/**
 * @author itguoy
 * @date 2021-12-16 10:55
 */
public class GMqProducer {

    private GMqBroker broker;

    public GMqProducer(GMqBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, GmqMessage message) {
        GMq gMq = broker.findGMq(topic);
        if (null == gMq) {
            throw new RuntimeException("Topic[" + topic + "] dosen't exist.");
        }
        return gMq.send(message);
    }
}
