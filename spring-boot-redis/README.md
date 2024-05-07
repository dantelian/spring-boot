# spring-boot-redis

## redis tool

> 工具类：RedisUtil

## 发布订阅

### 方案一

- 配置 RedisSubConfig
- 订阅监听 RedisMsgPubSubListener
- 发布方法 /order/publish

````
// 反序列化配置使用的这个
redisTemplate.setValueSerializer(new StringRedisSerializer());
````

### 方案二

- 配置 RedisSubConfig
- redis消息接收类 ReceiverRedisMessage
- 发布方法 /order/publish

> 参考：<https://blog.csdn.net/printf88/article/details/122337464>

## 加锁
> /order/tryLock


