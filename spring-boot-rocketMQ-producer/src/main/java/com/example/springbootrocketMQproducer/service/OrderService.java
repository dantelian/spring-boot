package com.example.springbootrocketMQproducer.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.springbootrocketMQproducer.model.entity.Order;

public interface OrderService extends IService<Order> {

    Boolean save(Order order);

    Order findById(String id);
}
