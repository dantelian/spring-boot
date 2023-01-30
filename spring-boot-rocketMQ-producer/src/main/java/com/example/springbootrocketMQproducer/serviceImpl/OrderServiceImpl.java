package com.example.springbootrocketMQproducer.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.springbootrocketMQproducer.mapper.OrderMapper;
import com.example.springbootrocketMQproducer.model.entity.Order;
import com.example.springbootrocketMQproducer.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: spring-boot-rocketMQ
 * @description:
 * @author: ddd
 * @create: 2023-01-29 17:41
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    @Transactional
    public Boolean save(Order order) {
        Boolean flag = this.insert(order);
        System.out.println(1/0);
        return flag;
    }

    @Override
    public Order findById(String id) {
        return this.baseMapper.selectById(id);
    }
}
