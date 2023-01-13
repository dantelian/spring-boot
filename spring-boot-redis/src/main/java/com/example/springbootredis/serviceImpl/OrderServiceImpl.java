package com.example.springbootredis.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.example.springbootredis.common.constants.OrderConstants;
import com.example.springbootredis.common.util.RedisUtil;
import com.example.springbootredis.model.entity.Order;
import com.example.springbootredis.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @program: spring-boot-redis
 * @description:
 * @author: ddd
 * @create: 2023-01-12 09:58
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public void tryLock(String order) {
        String lockName = "aa";
        String lockValue = order;
        if (RedisUtil.LockOps.getLockUntilTimeout(lockName, lockValue, 6, TimeUnit.SECONDS, 10000)) {
            System.out.println(order + "获取锁成功");
        } else {
            System.out.println(order + "获取锁失败");
        }

        try {
            System.out.println(order + "do sleep");
            Thread.sleep(5000);
            System.out.println(order + "end sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RedisUtil.LockOps.releaseLock(lockName, lockValue);
    }

    @Override
    public void publish(String msg) {
//        RedisUtil.MsgOps.convertAndSend(OrderConstants.REDIS_MSG_ORDER, msg);

        Order order = new Order();
        order.setId(msg);
        order.setNum(1);
        order.setDay(LocalDateTime.now());
        RedisUtil.MsgOps.convertAndSend(OrderConstants.REDIS_MSG_PHONE, JSON.toJSONString(order));
    }
}
