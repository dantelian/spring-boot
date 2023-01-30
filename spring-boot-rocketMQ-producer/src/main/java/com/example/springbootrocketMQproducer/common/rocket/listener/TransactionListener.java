package com.example.springbootrocketMQproducer.common.rocket.listener;

import com.example.springbootrocketMQproducer.model.entity.Order;
import com.example.springbootrocketMQproducer.service.OrderService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * @program: spring-boot-rocketMQ
 * @description:
 * @author: ddd
 * @create: 2023-01-29 17:21
 **/
@RocketMQTransactionListener
public class TransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private OrderService orderService;

    /**
     * 执行本地事务
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.printf("#### executeLocalTransaction  transactionId=%s %n", transId);
        //执行本地事务，并记录日志
        orderService.save((Order) o);
        //执行成功，可以提交事务
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 检查本地事务是否成功
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.printf("#### checkLocalTransaction transactionId=%s %n", transId);
        Order order = orderService.findById(transId);
        if (order != null) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
