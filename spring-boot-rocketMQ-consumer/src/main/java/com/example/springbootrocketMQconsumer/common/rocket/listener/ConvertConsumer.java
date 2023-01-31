package com.example.springbootrocketMQconsumer.common.rocket.listener;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @program: spring-boot-rocketMQ-consumer
 * @description:
 * @author: ddd
 * @create: 2023-01-31 17:12
 **/
@Service
@RocketMQMessageListener(
        topic = "${topic.convert}"
        ,consumerGroup = "${spring.application.name}-${topic.convert}-consumer3"
        ,selectorType = SelectorType.SQL92
        ,selectorExpression = "v=1"
)
public class ConvertConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        System.out.println("ConvertConsumer消费消息："+new String(message.getBody()));
        System.out.println("ConvertConsumer消费消息："+message.getProperties());
    }
}
