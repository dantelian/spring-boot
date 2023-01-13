package com.example.springbootcommon.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: spring-boot-common
 * @description:
 * @author: ddd
 * @create: 2023-01-13 11:19
 **/
@Data
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer num;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;



}
