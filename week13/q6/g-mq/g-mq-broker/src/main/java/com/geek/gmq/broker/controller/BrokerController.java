package com.geek.gmq.broker.controller;

import com.alibaba.fastjson.JSON;
import com.geek.gmq.broker.service.BrokerService;
import com.geek.gmq.common.Gmq;
import com.geek.gmq.common.GmqMessage;
import com.geek.gmq.common.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author itguoy
 * @date 2021-12-16 15:11
 */
@RestController
public class BrokerController {


    @Autowired
    private BrokerService brokerService;


    @PostMapping("/{topic}")
    public RespBean saveMsg(@PathVariable String topic, @RequestBody GmqMessage message) {
        brokerService.createTopic(topic);

        Gmq gmq = brokerService.findGmq(topic);
        if (null == gmq) {
            String errMsg = "Topic[" + topic + "] dosen't exist.";
            return RespBean.fail(400, errMsg);
        }
        boolean b = gmq.send(message);
        if (b) {
            System.out.println("broker接收到producer消息[" + JSON.toJSONString(message.getBody()) + "]");
            return RespBean.success();
        } else {
            return RespBean.fail(400, "unKnow error");
        }
    }

    @GetMapping("/{topic}/{consumerId}")
    public RespBean consumeMsg(@PathVariable String topic, @PathVariable String consumerId) {

        Gmq gmq = brokerService.findGmq(topic);
        if (null == gmq) {
            String errMsg = "Topic[" + topic + "] dosen't exist.";
            return RespBean.fail(400, errMsg);
        }

        GmqMessage message = gmq.poll(consumerId);

        return RespBean.success(message);
    }

    @GetMapping("/ack/{topic}/{consumerId}")
    public RespBean ack(@PathVariable String topic, @PathVariable String consumerId) {
        System.out.println("消费者确认消费成功");
        Gmq gmq = brokerService.findGmq(topic);
        if (null == gmq) {
            String errMsg = "Topic[" + topic + "] dosen't exist.";
            return RespBean.fail(400, errMsg);
        }

        gmq.incConsumerOffset(consumerId);
        return RespBean.success();
    }

    @GetMapping("/{topic}/{consumerId}/{offset}")
    public RespBean consumeOffset(@PathVariable String topic, @PathVariable String consumerId, @PathVariable int offset) {
        Gmq gmq = brokerService.findGmq(topic);
        if (null == gmq) {
            String errMsg = "Topic[" + topic + "] dosen't exist.";
            return RespBean.fail(400, errMsg);
        }

        GmqMessage message = gmq.poll(consumerId, offset);
        return RespBean.success(message);
    }


}
