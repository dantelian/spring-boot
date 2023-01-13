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
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.concurrent.CountDownLatch;

/**
 * @program: spring-boot-redis
 * @description:
 * @author: ddd
 * @create: 2023-01-12 16:31
 **/
@Configuration
public class RedisSubConfig {

    // 方案一
    /*@Bean
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
    }*/

    // 方案二
    /**
     * 创建连接工厂
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter,
                                                   MessageListenerAdapter listenerAdapterWang,
                                                   MessageListenerAdapter listenerAdapterTest2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //（不同的监听器可以收到同一个频道的信息）接受消息的频道
        container.addMessageListener(listenerAdapter, new PatternTopic(OrderConstants.REDIS_MSG_PHONE));

        container.addMessageListener(listenerAdapterWang, new PatternTopic(OrderConstants.REDIS_MSG_PHONE));

        container.addMessageListener(listenerAdapterTest2, new PatternTopic(OrderConstants.REDIS_MSG_PHONETEST2));
        return container;
    }
    /**
     * 绑定消息监听者和接收监听的方法
     *
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    @Bean
    public MessageListenerAdapter listenerAdapterWang(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageWang");
    }
    /**
     * 绑定消息监听者和接收监听的方法
     *
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapterTest2(ReceiverRedisMessage receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage2");
    }
    /**
     * 注册订阅者
     *
     * @param latch
     * @return
     */
    @Bean
    ReceiverRedisMessage receiver(CountDownLatch latch) {
        return new ReceiverRedisMessage(latch);
    }
    /**
     * 计数器，用来控制线程
     *
     * @return
     */
    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);//指定了计数的次数 1
    }

}
