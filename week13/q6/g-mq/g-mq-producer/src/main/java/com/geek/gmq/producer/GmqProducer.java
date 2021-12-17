package com.geek.gmq.producer;

import com.alibaba.fastjson.JSON;
import com.geek.gmq.common.GmqMessage;
import com.geek.gmq.common.RespBean;
import com.geek.gmq.producer.config.BrokerConfig;
import lombok.SneakyThrows;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author itguoy
 * @date 2021-12-16 15:19
 */
@Component
public class GmqProducer {

    @Autowired
    BrokerConfig brokerConfig;

    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder().build();
    }

    @SneakyThrows
    public boolean send(String topic, GmqMessage message) {

        String url = brokerConfig.getUrl() + "/" + topic;

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),
                        JSON.toJSONString(message)))
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        String retJson = response.body().string();
        RespBean respBean = JSON.parseObject(retJson, RespBean.class);
        return respBean.getCode() == 200;

    }


}
