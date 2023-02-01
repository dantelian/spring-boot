# spring-boot-rocketMQ-consumer

RocketMQ消息队列（消费者）

## 消费消息

当出现消息量大、网络抖动，消息重复就会成为大概率事件，解决方案是，为消息添加唯一标识（例如消息key），使消费者对消息进行消 费判断来避免重复消费

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


## 重置消费位点
若消费者分组的初始消费位点或当前消费位点不符合您的业务预期，您可以通过重置消费位点调整您的消费进度。

适用场景:
- 初始消费位点不符合需求：因初始消费位点为当前队列的最大消息位点，即客户端会直接从最新消息开始消费。若业务上线时需要消费部分历史消息，您可以通过重置消费位点功能消费到指定时刻前的消息。
- 消费堆积快速清理：当下游消费系统性能不足或消费速度小于生产速度时，会产生大量堆积消息。若这部分堆积消息可以丢弃，您可以通过重置消费位点快速将消费位点更新到指定位置，绕过这部分堆积的消息，减少下游处理压力。
- 业务回溯，纠正处理：由于业务消费逻辑出现异常，消息被错误处理。若您希望重新消费这些已被处理的消息，可以通过重置消费位点快速将消费位点更新到历史指定位置，实现消费回溯。


参考：  
[消费者]<https://blog.csdn.net/UnicornRe/article/details/117849396>  
[重置消费位点]<https://blog.csdn.net/qq_32940105/article/details/123089858>  
[消息重试机制和死信队列]<https://www.cnblogs.com/xjwhaha/p/15868408.html#_label0_1>

