# spring-boot-rocketMQ-producer

RocketMQ消息队列（生产者）

## 发送消息

- 普通消息  
    普通消息一般应用于微服务解耦、事件驱动、数据集成等场景，这些场景大多数要求数据传输通道具有可靠传输的能力，且对消息的处理时机、处理顺序没有特别要求。
    - 底层调用的syncSend方法，但是不接受返回结果
    > /rocketMQ/send
    - 发送消息,等待发送消息返回的结果
    > /rocketMQ/syncSend
    - 发送消息,通过回调函数根据发送状态处理相对于逻辑
    > /rocketMQ/asyncSend
    - 单向消息，不等待返回
    > /rocketMQ/sendOneWay

- 定时/延时消息  
    在分布式定时调度触发、任务超时处理等场景，需要实现精准、可靠的定时事件触发。  
    > 方案一: /rocketMQ/sendSchedule  
    方案二: /rocketMQ/sendScheduleProducer

- 顺序消息  
    顺序消息消费失败，rocketmq会不断进行重试，直至消费成功。  
    要注意重复消费的问题，详见 spring-boot-rocketMQ-consumer
    - 同步顺序发送
        - public SendResult syncSendOrderly(String destination, Object payload, String hashKey) {
        - public SendResult syncSendOrderly(String destination, Object payload, String hashKey, long timeout) {
        - public SendResult syncSendOrderly(String destination, Message<?> message, String hashKey) {
        - public SendResult syncSendOrderly(String destination, Message<?> message, String hashKey, long timeout) {
    - 异步顺序发送
        - public void asyncSendOrderly(String destination, Object payload, String hashKey, SendCallback sendCallback) {
            > /rocketMQ/sendSequence
        - public void asyncSendOrderly(String destination, Object payload, String hashKey, SendCallback sendCallback, long timeout) {
        - public void asyncSendOrderly(String destination, Message<?> message, String hashKey, SendCallback sendCallback) {
        - public void asyncSendOrderly(String destination, Message<?> message, String hashKey, SendCallback sendCallback, long timeout) {
    - 单向顺序发送
        - public void sendOneWayOrderly(String destination, Object payload, String hashKey) {
        - public void sendOneWayOrderly(String destination, Message<?> message, String hashKey) {
- 事务消息  
    配合TransactionListener类使用
    > /rocketMQ/sendMessageInTransaction






## 表sql
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
