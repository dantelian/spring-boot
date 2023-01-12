package com.example.springbootredis.service;

/**
 * @program: spring-boot-redis
 * @description:
 * @create: 2023-01-12 09:54
 **/
public interface OrderService {

    // redis 锁
    void tryLock(String order);

}
