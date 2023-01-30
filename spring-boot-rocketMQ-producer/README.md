# spring-boot-rocketMQ-producer

RocketMQ消息队列（生产者）

## 发送消息

### 普通消息

- 底层调用的syncSend方法，但是不接受返回结果
> /rocketMQ/send
- 发送消息,等待发送消息返回的结果
> /rocketMQ/syncSend
- 发送消息,通过回调函数根据发送状态处理相对于逻辑
> /rocketMQ/asyncSend

### 定时/延时消息
> 方案一: /rocketMQ/sendSchedule  
> 方案二: /rocketMQ/sendScheduleProducer

### 顺序消息

### 事务消息
> /rocketMQ/sendMessageInTransaction








##表sql
````sql
CREATE TABLE `order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
````

参考：
<https://blog.csdn.net/ming19951224/article/details/119523197>
<https://rocketmq.apache.org/zh/docs/featureBehavior/03fifomessage>
