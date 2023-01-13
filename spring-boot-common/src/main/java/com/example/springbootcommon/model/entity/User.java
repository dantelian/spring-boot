package com.example.springbootcommon.model.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户名
     */
    @Excel(name = "用户名")
    private String username;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Integer age;

    /**
     * 性别,0表示男，1表示女
     */
    @Excel(name = "性别",replace = {"男_0", "女_1"})
    private String sex;

    /**
     * 籍贯
     */
    @Excel(name = "籍贯")
    private String address;

    @Excel(name = "时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 40)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;



}
