package com.example.springbootrabbitMQproducer.common.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列
 * 需安装CustomExchange插件
 * 会根据延时时长，及时消费
 */
@Configuration
public class CustomRabbitConfig {

    public static String DELAY_EXCHANGE = "DELAY.EXCHANGE";

    public static String DELAY_QUEUE = "DELAY.QUEUE";

    public static String DELAY_ROUTING_KEY = "delay-message";

    @Bean
    public CustomExchange delayExchange(){
        Map<String,Object> arg = new HashMap<>(3);
        arg.put("x-delayed-type","direct");
        CustomExchange customExchange = new CustomExchange(DELAY_EXCHANGE,"x-delayed-message",true,false,arg);
        return customExchange;
    }

    @Bean
    public Queue delayQueue(){
        return new Queue(CustomRabbitConfig.DELAY_QUEUE);
    }

    @Bean
    public Binding delayBind(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY).noargs();
    }

}
