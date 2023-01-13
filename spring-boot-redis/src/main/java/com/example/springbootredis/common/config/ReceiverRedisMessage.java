package com.example.springbootredis.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * @program: spring-boot-redis
 * @description: redis消息接收类
 * @author: ddd
 * @create: 2023-01-13 09:57
 **/
@Slf4j
public class ReceiverRedisMessage {

    private CountDownLatch latch;

    @Autowired
    public ReceiverRedisMessage(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * 队列消息接收方法
     *
     * @param jsonMsg
     */
    public void receiveMessage(String jsonMsg) {
        log.info("[receiveMessage开始消费REDIS消息队列phone数据...]");
        try {
            System.out.println(jsonMsg);
            log.info("[receiveMessage消费REDIS消息队列phone数据成功.]");
        } catch (Exception e) {
            log.error("[receiveMessage消费REDIS消息队列phone数据失败，失败信息:{}]", e.getMessage());
        }
        latch.countDown();
    }

    public void receiveMessageWang(String jsonMsg) {
        log.info("[receiveMessageWang开始消费REDIS消息队列phone数据...]");
        try {
            System.out.println(jsonMsg);
            log.info("[receiveMessageWang消费REDIS消息队列phone数据成功.]");
        } catch (Exception e) {
            log.error("[receiveMessageWang消费REDIS消息队列phone数据失败，失败信息:{}]", e.getMessage());
        }
        latch.countDown();
    }

    /**
     * 队列消息接收方法
     *
     * @param jsonMsg
     */
    public void receiveMessage2(String jsonMsg) {
        log.info("[receiveMessage2开始消费REDIS消息队列phoneTest2数据...]");
        try {
            System.out.println(jsonMsg);
            /**
             *  此处执行自己代码逻辑 操作数据库等
             */

            log.info("[receiveMessage2消费REDIS消息队列phoneTest2数据成功.]");
        } catch (Exception e) {
            log.error("[receiveMessage2消费REDIS消息队列phoneTest2数据失败，失败信息:{}]", e.getMessage());
        }
        latch.countDown();
    }

}
