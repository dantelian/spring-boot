# spring-boot-rabbitMQ-producer

RabbitMQ消息队列（生产者）

## 发送消息

### Direct Exchange 

直连型交换机，根据消息携带的路由键将消息投递给对应队列。

大致流程，有一个队列绑定到一个直连交换机上，同时赋予一个路由键 routing key 。
然后当一个消息携带着路由值为X，这个消息通过生产者发送给交换机时，交换机就会根据这个路由值X去寻找绑定值也是X的队列。

直连交换机是一对一，如果配置多台监听绑定到同一个直连交互的同一个队列，会以轮询的方式对消息进行消费，而且不存在重复消费。

### Fanout Exchange

扇型交换机，这个交换机没有路由键概念，就算你绑了路由键也是无视的。 这个交换机在接收到消息后，会直接转发到绑定到它上面的所有队列。

### Topic Exchange

主题交换机，这个交换机其实跟直连交换机流程差不多，但是它的特点就是在它的路由键和绑定键之间是有规则的。
简单地介绍下规则：

\*  (星号) 用来表示一个单词 (必须出现的)  
\#  (井号) 用来表示任意数量（零个或多个）单词

> 通配的绑定键是跟队列进行绑定的，举个小例子  
队列Q1 绑定键为 *.TT.*          队列Q2绑定键为  TT.#  
如果一条消息携带的路由键为 A.TT.B，那么队列Q1将会收到；  
如果一条消息携带的路由键为TT.AA.BB，那么队列Q2将会收到；

### Custom Exchange

自定义交换机，可实现动态定时任务。是一对一消费，如果配置多台监听绑定到同一个直连交互的同一个队列，会以轮询的方式对消息进行消费，而且不存在重复消费。会根据延时时长，及时消费。

### 死信队列

可以实现定时任务，发送的消息会顺序执行，即使后发的消息延时时间短也会等之前的消息消费了才会消费
实例：[DeadDelayRabbitConfig.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2FspringbootrabbitMQproducer%2Fcommon%2Fconfig%2FDeadDelayRabbitConfig.java)

参考：  
[发布订阅] <https://blog.csdn.net/qq_35387940/article/details/100514134>  
[安装延时队列插件（CustomExchange）] <https://blog.csdn.net/javaee_gao/article/details/128614439>

