package com.example.springbootrocketMQconsumer.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: spring-boot-rocketMQ
 * @description:
 * @author: ddd
 * @create: 2023-01-29 11:57
 **/
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

}
