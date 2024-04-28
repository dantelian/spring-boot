package com.example.springbootmybatis.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * 用字符串方式返回：
     * 方案一：@JsonFormat(shape =JsonFormat.Shape.STRING)
     * 方案二：@JsonSerialize(using = ToStringSerializer.class)
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
