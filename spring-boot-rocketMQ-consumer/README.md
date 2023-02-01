# spring-boot-rocketMQ-consumer

RocketMQ消息队列（消费者）

## 消费消息

- OrderConsumer  
    - 传递order对象
- StringConsumer
    - 传递string对象
- TagConsumer
    - 根据tag过滤
- SQL92Consummer
    - 根据SQL92过滤消息
- ConvertConsumer
    - convert发送消息，可使用MessageExt进行接收消费

## @RocketMQMessageListener

- topic = "${topic}"    
    主题
- consumerGroup = "${consumerGroup}"    
    消费者组，如果consumerGroup的名称不同, 则会产生重复消费的情况
- consumeMode  
    消费模式
    - consumeMode = ConsumeMode.CONCURRENTLY  
        并行消费
    - consumeMode = ConsumeMode.ORDERLY  
        有序消费
- messageModel  
    消息模型
    - messageModel = MessageModel.CLUSTERING  
        集群
    - messageModel = MessageModel.BROADCASTING  
        广播
- selectorType  
    过滤方式，默认值SelectorType.TAG
    - selectorType = SelectorType.TAG   
        指明了消息选择通过tag的方法
        > 参考：TagConsumer
    - selectorType = SelectorType.SQL92
        指明了消息过滤使用SQL92方式
        > 参考：SQL92Consummer
- selectorExpression    
    过滤条件，默认值*，代表全部。
    - selectorExpression = "tag1||tag2"  
        配合tag方式，指定过滤tag；
        > 参考：TagConsumer
    - selectorExpression = "v=1"  
        配合SQL92方式，指明了只能接收消息属性（header）中v=1的消息； 
        > 参考：SQL92Consummer
        - 数字比较，如>，>=，<，<=，BETWEEN，=；
        - 字符比较，如：=，<>，IN；
        - IS NULL or IS NOT NULL;
        - 逻辑运算符：AND, OR, NOT;
        > ①数值，如：123, 3.1415；  
          ②字符, 如：‘abc’, 必须使用单引号;  
          ③NULL，特殊常量  
          ④Boolean, TRUE or FALSE;  
- consumeThreadMax  
    最大线程数，默认值64
- consumeTimeout  
    超时时间，默认值30000ms
- accessKey  
    默认值${rocketmq.consumer.access-key:}
- secretKey  
    默认值${rocketmq.consumer.secret-key:}
- enableMsgTrace    
    启用消息轨迹，默认值 true
- customizedTraceTopic  
    自定义的消息轨迹主题；     
    默认值${rocketmq.consumer.customized_trace_topic:}     
    没有配置此配置则使用默认的主题 
- nameServer    
    命名服务器地址，默认值${rocketmq.name-server:}
- accessChannel  
    默认值${rocketmq.access_channel}



参考：  
消费者：<https://blog.csdn.net/UnicornRe/article/details/117849396>



