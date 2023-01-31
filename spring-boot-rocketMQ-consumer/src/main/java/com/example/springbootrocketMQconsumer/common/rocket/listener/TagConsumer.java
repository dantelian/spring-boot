package com.example.springbootrocketMQconsumer.common.rocket.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @program: spring-boot-rocketMQ-consumer
 * @description: 根据tag过滤消息
 * @author: ddd
 * @create: 2023-01-31 14:21
 **/
@Service
@RocketMQMessageListener(
        topic = "${topic.string}"
        ,consumerGroup = "${spring.application.name}-${topic.string}-consumer2"
        ,selectorType = SelectorType.TAG
        ,selectorExpression = "tag1||tag2"
)
public class TagConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        System.out.println("TagConsumer开始消费:"+ msg);
//        if(true){
//            //模拟出现异常,出现异常后这个任务还会被重试消费
//            System.out.println(1/0);
//        }
    }
}
