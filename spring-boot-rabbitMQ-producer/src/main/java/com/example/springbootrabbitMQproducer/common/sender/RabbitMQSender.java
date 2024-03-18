package com.example.springbootrabbitMQproducer.common.sender;

import com.example.springbootrabbitMQproducer.common.config.CustomRabbitConfig;
import com.example.springbootrabbitMQproducer.common.config.DeadDelayRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: spring-boot-rabbitMQ-producer
 * @description:
 * @author: ddd
 * @create: 2023-04-06 15:18
 **/
@Component
public class RabbitMQSender {

    @Resource
    RabbitTemplate rabbitTemplate;  // 使用RabbitTemplate,这提供了接收/发送等等方法

    public void sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
    }

    public void sendTopicMessageMan() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
    }

    public void sendTopicMessageWoman() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
    }

    public void sendCustomDelayed() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: custom delayed ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> delayedMap = new HashMap<>();
        delayedMap.put("messageId", messageId);
        delayedMap.put("messageData", messageData);
        delayedMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend(CustomRabbitConfig.DELAY_EXCHANGE, CustomRabbitConfig.DELAY_ROUTING_KEY, delayedMap, message -> {
            message.getMessageProperties().setDelay(1000 * 10); // 设置延时时长
            return message;
        });
    }

    // 发送的消息会顺序执行，即使后发的消息延时时间短也会等之前的消息消费了才会消费
    public void sendDeadDelayed() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: dead delay ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> deadMap = new HashMap<>();
        deadMap.put("messageId", messageId);
        deadMap.put("messageData", messageData);
        deadMap.put("createTime", createTime);

        // 方案一 ： 配置x-message-ttl
//        rabbitTemplate.convertAndSend(DeadDelayRabbitConfig.FORMAL_EXCHANGE, DeadDelayRabbitConfig.FORMAL_ROUNTE_KEY, deadMap);

        // 方案二 ： 手动指定时长
        rabbitTemplate.convertAndSend(
                //发送至订单交换机
                DeadDelayRabbitConfig.FORMAL_EXCHANGE,
                //routingKey
                DeadDelayRabbitConfig.FORMAL_ROUNTE_KEY,
                deadMap
                , message -> {
                    // 如果配置了 params.put("x-message-ttl", 5 * 1000);
                    // 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
                    message.getMessageProperties().setExpiration("10000"); // 延时时长（毫秒）
                    return message;
                });
    }

}
