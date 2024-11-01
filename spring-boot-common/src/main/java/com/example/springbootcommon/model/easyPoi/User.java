package com.example.springbootcommon.model.easyPoi;

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

    // 自增序号
    @Excel(name = "序号", format = "isAddIndex")
    private Integer index = 1;

    private String id;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "性别",replace = {"男_0", "女_1"})
    private String sex;

    @Excel(name = "籍贯")
    private String address;

    @Excel(name = "时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 40)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    @Excel(name = "图片", type = 2)
    private String photo;



}
