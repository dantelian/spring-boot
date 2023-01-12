package com.example.springbootredis.common.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: spring-boot-redis
 * @description: Redis监听订阅事件
 * @author: ddd
 * @create: 2023-01-12 16:00
 **/
@Component
public class RedisMsgPubSubListener implements MessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        // 获取消息
        byte[] messageBody = message.getBody();
        // 使用值序列化器转换
        Object msg = redisTemplate.getValueSerializer().deserialize(messageBody);
        // 获取监听的频道
        byte[] channelByte = message.getChannel();
        // 使用字符串序列化器转换
        Object channel = redisTemplate.getStringSerializer().deserialize(channelByte);
        // 渠道名称转换
        String patternStr = new String(bytes);
        System.out.println(patternStr);
        System.out.println("---频道---: " + channel);
        System.out.println("---消息内容---: " + msg);
    }
}
