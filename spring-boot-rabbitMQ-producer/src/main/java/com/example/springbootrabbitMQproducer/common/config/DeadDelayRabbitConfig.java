package com.example.springbootrabbitMQproducer.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列实现定时任务
 * 发送的消息会顺序执行，即使后发的消息延时时间短也会等之前的消息消费了才会消费
 */
@Configuration
public class DeadDelayRabbitConfig {

    //队列
    public static final String FORMAL_QUEUE = "formal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    //交换机
    public static final String FORMAL_EXCHANGE = "formal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchaneg";

    //路由key
    public static final String FORMAL_ROUNTE_KEY = "formal_rounte_key";
    public static final String DEAD_ROUNTE_KEY = "dead_route_key";

    /**
     * 普通队列交换机声明
     * 交换机类型：topic：处理路由键，按模式匹配，向符合规则的队列投递消息
     * name 交换机名称
     * durable 是否持久化
     * autoDelete 是否删除
     * arguments 用于设置其他参数
     * @return
     */
    @Bean
    TopicExchange getFormalExchange(){
        return new TopicExchange(FORMAL_EXCHANGE,true,false,null);
    }

    /**
     * 声明普通队列，并设置与死信队列联系
     * name 队列名称
     * durable 是否持久化
     * exclusive 是否排外 如果是排外的，该队列 仅对首次声明它的连接（Connection）可见，是该Connection私有的，
     * 类似于加锁，并在连接断开connection.close()时自动删除
     * autoDelete 是否删除
     * arguments 用于设置其他参数
     * @return
     */
    @Bean
    public Queue getFormalQueue(){
        Map<String, Object> map = new HashMap<>();
        // 消息超时后发往的死信交换器
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        // 消息超时后选择的路由键
        map.put("x-dead-letter-routing-key",DEAD_ROUNTE_KEY);

        // 方式一：设置队列超时时间, 也可以在推送消息时设定
//        map.put("x-message-ttl", 5000); // 延时时长（毫秒）

        // 方式二：设置队列最大长度, 超长后发往死信队列
//        map.put("x-max-length",5);
        return new Queue(FORMAL_QUEUE,true,false,false,map);
    }

    /**
     * 将普通队列和交换机绑定
     * destination：目标队列或交换器
     * destinationType：DesdinationType指出目标是交换器还是对列
     * exchange：交换机
     * routingKey：路由key
     * arguments：参数设置
     * @return
     */
    @Bean
    Binding bingFormalQueue(){
        return new Binding(FORMAL_QUEUE, Binding.DestinationType.QUEUE,FORMAL_EXCHANGE,FORMAL_ROUNTE_KEY,null);
    }


    /**
     * 声明死信队列交换机
     * @return
     */
    @Bean
    TopicExchange getDeadExchange(){
        return new TopicExchange(DEAD_EXCHANGE,true,false,null);
    }


    /**
     * 声明死信队列
     * @return
     */
    @Bean
    public Queue getDeadQueue(){
        return new Queue(DEAD_QUEUE,true,false,false, null);
    }

    /**
     * 将死信队列和交换机绑定
     * @return
     */
    @Bean
    public Binding bingDeadQueue(){
        return new Binding(DEAD_QUEUE, Binding.DestinationType.QUEUE,DEAD_EXCHANGE,DEAD_ROUNTE_KEY,null);
    }

}
