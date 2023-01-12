package com.example.springbootredis.common.config;

import com.example.springbootredis.common.constants.OrderConstants;
import com.example.springbootredis.common.listener.RedisMsgPubSubListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @program: spring-boot-redis
 * @description:
 * @author: ddd
 * @create: 2023-01-12 16:31
 **/
@Configuration
public class RedisSubConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisMsgPubSubListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        //订阅频道redis.news 和 redis.life  这个container 可以添加多个 messageListener
        container.addMessageListener(listener, new ChannelTopic(OrderConstants.REDIS_MSG_ORDER));
        //container.addMessageListener(listener, new ChannelTopic("redis.news"));

        //序列化对象（特别注意：发布的时候需要设置序列化；订阅方也需要设置序列化）
//        Jackson2JsonRedisSerializer seria = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        seria.setObjectMapper(objectMapper);
//        container.setTopicSerializer(seria);

        return container;
    }

}
