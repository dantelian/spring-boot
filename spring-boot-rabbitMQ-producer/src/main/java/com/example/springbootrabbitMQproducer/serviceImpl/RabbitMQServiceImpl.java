package com.example.springbootrabbitMQproducer.serviceImpl;

import com.example.springbootrabbitMQproducer.common.sender.RabbitMQSender;
import com.example.springbootrabbitMQproducer.service.RabbitMQService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: spring-boot-rabbitMQ-producer
 * @description:
 * @author: ddd
 * @create: 2023-04-06 15:05
 **/
@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    // 需要使用Spring容器来管理，否则获取的rabbitTemplate会为null
    @Resource
    private RabbitMQSender rabbitMQSender;

    @Override
    public boolean sendMessage(String type) {
        boolean flag = true;

        switch (type) {
            case "direct":
                rabbitMQSender.sendDirectMessage();
                break;
            case "topicMan":
                rabbitMQSender.sendTopicMessageMan();
                break;
            case "topicWoman":
                rabbitMQSender.sendTopicMessageWoman();
                break;
            default:
                flag = false;
        }

        return flag;
    }
}
