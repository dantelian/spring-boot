package com.example.springbootmongodb.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 指定集合的名称如果不指定
 * 默认为当前类名小写
 */
@Data
@Document(collection = "t_user")
public class User {

    @Id
    private String id;

    @Field("name") // 指定该实体类字段对应到 MongoDB 文档中的字段名
    @Indexed(name = "IDX_NAME") // 在字段上创建默认的单字段索引
    private String name;
    private Integer age;
    private String city;

    // 创建时间
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;

    // 最后修改时间
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModifiedDate;

}
