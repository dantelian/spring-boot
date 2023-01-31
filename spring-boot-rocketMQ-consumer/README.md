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
    - consumeMode = ConsumeMode.CONCURRENTLY  
        同时消费
    - consumeMode = ConsumeMode.ORDERLY  
        有序消费
- selectorExpression    默认值*，代表全部。
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
- selectorType  默认值SelectorType.TAG
    - selectorType = SelectorType.TAG   
        指明了消息选择通过tag的方法
        > 参考：TagConsumer
    - selectorType = SelectorType.SQL92
        指明了消息过滤使用SQL92方式
        > 参考：SQL92Consummer

参考：  
消费者：<https://blog.csdn.net/UnicornRe/article/details/117849396>



