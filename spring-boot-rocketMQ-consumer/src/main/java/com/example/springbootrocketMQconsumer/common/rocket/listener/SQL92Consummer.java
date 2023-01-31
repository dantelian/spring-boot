package com.example.springbootrocketMQconsumer.common.rocket.listener;

import com.example.springbootrocketMQconsumer.model.entity.Order;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @program: spring-boot-rocketMQ-consumer
 * @description: 根据SQL92过滤消息
 * @author: ddd
 * @create: 2023-01-31 14:42
 **/
@Service
@RocketMQMessageListener(
        topic = "${topic.order}"
        ,consumerGroup = "${spring.application.name}-${topic.order}-consumer3"
        ,selectorType = SelectorType.SQL92
        ,selectorExpression = "v=1"
)
public class SQL92Consummer implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order order) {
        System.out.println("SQL92Consummer开始消费: " +  order.getName() + "," + order.getId());
//        if(true){
//            //模拟出现异常,出现异常后这个任务还会被重试消费
//            System.out.println(1/0);
//        }
    }
}
