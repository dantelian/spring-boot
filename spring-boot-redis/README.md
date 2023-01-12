# spring-boot-redis

## redis tool

> 工具类：RedisUtil

## 发布订阅

- 配置 RedisSubConfig
- 订阅监听 RedisMsgPubSubListener
- 发布方法 /order/publish

````
// 反序列化配置使用的这个
redisTemplate.setValueSerializer(new StringRedisSerializer());
````






