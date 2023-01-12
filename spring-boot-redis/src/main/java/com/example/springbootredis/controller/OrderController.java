package com.example.springbootredis.controller;

import com.example.springbootredis.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-redis
 * @description:
 * @author: ddd
 * @create: 2023-01-12 11:24
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/tryLock")
    public void tryLock(String order) {
        orderService.tryLock(order);
    }

    @PostMapping("/publish")
    public void publish(String msg) {
        orderService.publish(msg);
    }

}
