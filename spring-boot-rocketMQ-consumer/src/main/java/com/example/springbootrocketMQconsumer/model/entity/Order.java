package com.example.springbootrocketMQconsumer.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: spring-boot-rocketMQ
 * @description:
 * @author: ddd
 * @create: 2023-01-29 11:57
 **/
@Data
@TableName("`order`")
public class Order extends Model<Order> {

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
