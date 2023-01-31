# spring-boot-rocketMQ-producer

RocketMQ消息队列（生产者）

## 发送消息

topic后可快速拼接tag。
> 例：<_topic_>:<_tag_>

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
    要注意重复消费的问题，可在消费端消费时进行重复判断。
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

## 消息重试和死信队列
  
- 当消费者消费时，如果抛出了异常，则会重新再次投递给该消费者。  
- 但消息不是一直就可以重新投递的，当重试次数达到默认的16次后（可以通过配置文件修改）如果对应的消息还没被成功消费的话，该消息就会投递到DLQ死信队列。
- 可以在控制台Topic列表中看到“DLQ”相关的Topic，默认命名是：%RETRY%消费组名称（重试Topic）%DLQ%消费组名称（死信Topic）。  
- 死信队列也可以被订阅和消费，并且也会过期。有效期与正常消息相同，均为 3 天，3 天后会被自动删除。因此，请在死信消息产生后的 3天内及时处理。


## 表sql
````sql
CREATE TABLE `order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';
````

参考：  
[官方文档]<https://rocketmq.apache.org/zh/docs/featureBehavior/03fifomessage>  
[发布订阅]<https://blog.csdn.net/ming19951224/article/details/119523197>  
[发布订阅]<https://blog.csdn.net/qq_43692950/article/details/111827904>  
[消息过滤]<https://blog.csdn.net/qq_26383975/article/details/125600949>

