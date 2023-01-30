# spring-boot-rocketMQ-producer

RocketMQ消息队列（生产者）

## 发布消息

- 底层调用的syncSend方法，但是不接受返回结果
> /rocketMQ/send
- 发送消息,等待发送消息返回的结果
> /rocketMQ/syncSend
- 发送消息,通过回调函数根据发送状态处理相对于逻辑
> /rocketMQ/asyncSend
- 发送事务消息
> /rocketMQ/sendMessageInTransaction

## 普通消息


## 定时/延时消息

## 顺序消息

## 事务消息









##表sql
````sql
CREATE TABLE `order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
````



