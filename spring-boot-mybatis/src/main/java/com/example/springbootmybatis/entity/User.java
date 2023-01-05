package com.example.springbootmybatis.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 角色id
     */
    private String roleid;
    /**
     * 部门id
     */
    private String deptid;
    /**
     * 状态(1：pc端  2：app端  3：删除）
     */
    private Integer status;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;
    /**
     * 保留字段
     */
    private String version;


    private String token;

    /**
     * 身份证号
     */
    @TableField("id_number")
    private String idNumber;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
