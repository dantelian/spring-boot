package com.example.springbootredis.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: spring-boot-redis
 * @description:
 * @author: ddd
 * @create: 2023-01-12 14:21
 **/
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer num;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime day;

}
