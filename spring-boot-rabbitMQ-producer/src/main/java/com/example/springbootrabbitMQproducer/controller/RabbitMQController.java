package com.example.springbootrabbitMQproducer.controller;

import com.example.springbootrabbitMQproducer.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-rabbitMQ-producer
 * @description:
 * @author: ddd
 * @create: 2023-04-06 15:03
 **/
@RestController
@RequestMapping("/rabbitMQ")
public class RabbitMQController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @GetMapping("/send")
    public String send(String type) {
        if (rabbitMQService.sendMessage(type)) {
            return "success!";
        }
        return "fail!";
    }

}
