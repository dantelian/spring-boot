package com.example.springbootrabbitMQconsumer.common.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 延时队列消费者
 */
@Component
@RabbitListener(queues = "DELAY.QUEUE")
public class DelayReceiver {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " DelayReceiver消费者收到消息  : " + testMessage.toString());
    }

}
