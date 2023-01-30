package com.example.springbootrocketMQproducer.controller;

import com.example.springbootrocketMQproducer.model.entity.Order;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: spring-boot-rocketMQ
 * @description:
 * @author: ddd
 * @create: 2023-01-29 11:25
 **/
@RestController
@RequestMapping("/rocketMQ")
public class RocketMQController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Value("${topic.string}")
    private String stringTopic;
    @Value("${topic.order}")
    private String orderTopic;

    /**
     * 底层调用的syncSend方法，但是不接受返回结果
     * 异步
     * @param topic 消息队列
     * @param msg   消息类型
     * @return
     */
    @GetMapping("/send")
    public String send(String topic, String msg) {
        // 发送异步消息,但是不会确认消息有没有被接收,日志可以这么发,如果是对数据一致性要去比较高的,建议使用下面的方法
        rocketMQTemplate.send(stringTopic, MessageBuilder.withPayload(String.format("test send method")).build());
        return "success!";
    }

    /**
     * 发送消息,等待发送消息返回的结果
     * 同步
     * @param topic
     * @param msg
     * @return
     */
    @GetMapping("/syncSend")
    public String syncSend(String topic, String msg) {
        // 发送同步消息,等待发送消息返回的结果
        SendResult sendResult = rocketMQTemplate.syncSend(stringTopic, "test syncSend method");
        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
            System.out.println("发送成功...");
        }
        return "success!";
    }

    /**
     * 发送消息,通过回调函数根据发送状态处理相对于逻辑
     * 同步
     * @param topic
     * @param msg
     * @return
     */
    @GetMapping("/asyncSend")
    public String asyncSend(String topic, String msg) {
        // 发送异步消息,回调里处理发送成功或失败逻辑
        for (int j = 1; j < 3; j++) {
            rocketMQTemplate.asyncSend(stringTopic, "test asyncSend method", new SendCallback() {
                @Override
                public void onSuccess(SendResult sr) {
                    if (sr.getSendStatus() == SendStatus.SEND_OK) {
                        System.out.println("async onSucess ok");
                    } else {
                        System.out.println("async onSucess fail");
                    }
                }

                @Override
                public void onException(Throwable var1) {
                    System.out.printf("async onException Throwable=%s %n", var1);
                }
            });
            System.out.printf("发送第%d条消息\n", j);
        }
        return "success!";
    }

    /**
     * 发送事务消息
     * @param topic
     * @param msg
     * @return
     */
    @GetMapping("/sendMessageInTransaction")
    public String sendMessageInTransaction(String topic, String msg) {
        Long orderId = System.currentTimeMillis();
        Order order = new Order();
        order.setId(orderId + "");
        order.setName("酒水订单");
        rocketMQTemplate.sendMessageInTransaction(orderTopic,
                MessageBuilder.withPayload(order)
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, orderId)
                        .build()
                , order);
        return "success!";
    }

}
