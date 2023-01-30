package com.example.springbootrocketMQconsumer.common.rocket.listener;

import com.example.springbootrocketMQconsumer.model.entity.Order;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @program: spring-boot-rocketMQ
 * @description:
 * @author: ddd
 * @create: 2023-01-29 11:56
 **/
@Service
@RocketMQMessageListener(topic = "${topic.order}", consumerGroup = "${spring.application.name}-${topic.order}-consumer1")
public class OrderConsumer implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order order) {
        System.out.println("OrderConsumer1开始消费：" + order.getName() + "," + order.getId());
    }
}
