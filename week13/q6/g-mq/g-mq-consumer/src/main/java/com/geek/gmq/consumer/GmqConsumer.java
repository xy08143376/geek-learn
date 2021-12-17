package com.geek.gmq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.geek.gmq.common.GmqMessage;
import com.geek.gmq.common.RespBean;
import com.geek.gmq.common.demo.Order;
import com.geek.gmq.consumer.config.BrokerConfig;
import com.geek.gmq.consumer.config.ConsumerConfig;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author itguoy
 * @date 2021-12-16 17:04
 */
@Component
public class GmqConsumer {

    @Autowired
    private BrokerConfig brokerConfig;

    @Autowired
    ConsumerConfig consumerConfig;

    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder().build();
    }


    @SneakyThrows
    public <T> GmqMessage<T> poll(Class<T> clazz) {
        String url = brokerConfig.getUrl().concat("/")
                .concat(consumerConfig.getTopic())
                .concat("/")
                .concat(consumerConfig.getConsumerId());
        RespBean<GmqMessage<T>> message = consumeMsg(clazz, url);

        return message.getData();
    }


    @SneakyThrows
    public <T> GmqMessage<T> poll(Class<T> clazz, int offset) {
        String url = brokerConfig.getUrl().concat("/")
                .concat(consumerConfig.getTopic())
                .concat("/")
                .concat(consumerConfig.getConsumerId())
                .concat("/")
                .concat(String.valueOf(offset));
        RespBean<GmqMessage<T>> message = consumeMsg(clazz, url);

        return message.getData();
    }

    private <T> RespBean<GmqMessage<T>> consumeMsg(Class<T> clazz, String url) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String retJson = response.body().string();
//        System.out.println(retJson);
        RespBean<GmqMessage<T>> message = JSON.parseObject(retJson, new TypeReference<RespBean<GmqMessage<T>>>(clazz) {
        });
        return message;
    }

    @SneakyThrows
    public void ack() {
        String url = brokerConfig.getUrl().concat("/ack/")
                .concat(consumerConfig.getTopic())
                .concat("/")
                .concat(consumerConfig.getConsumerId());

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String retJson = response.body().string();
        System.out.println(retJson);
    }


}
