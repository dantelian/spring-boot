# spring-boot-minio

## 功能

附件上传
> 单个：/file/upload  
> 多个：/file/uploads

附件下载

> /file/download  
> fileName是附件完整路径

## 附件表
> Attachments
````sql
CREATE TABLE `t_attachments` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `file_name` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名',
  `file_full_name` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件全名',
  `size` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件大小',
  `path` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件路径',
  `file_type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `ext` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件后缀',
  `save_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '存储文件名',
  `md5` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件MD5信息',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '版本',
  `deleted` int(11) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_bus` (`business_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='附件表';
````

