package com.example.springbootminio.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: spring-boot-minio
 * @description:
 * @author: ddd
 * @create: 2023-01-28 14:48
 **/
@Data
public class Attachments implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件全名
     */
    private String fileFullName;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件后缀
     */
    private String ext;
    /**
     * 存储文件名
     */
    private String saveName;
    /**
     * 文件MD5信息
     */
    private String md5;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 版本
     */
    private Integer version;
    /**
     * 是否删除
     */
    private Integer deleted;

}
