package com.example.springbootminio.controller;

import com.example.springbootminio.common.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: spring-boot-minio
 * @description:
 * @author: ddd
 * @create: 2023-01-16 14:04
 **/
@RestController
@RequestMapping("/file")
public class MinioController {

    @Autowired
    private MinioUtils minioUtils;
    @Value("${minio.endpoint}")
    private String address;
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Object upload(MultipartFile[] file) {
        List<String> upload =  minioUtils.upload(file);
        return address+"/"+bucketName+"/"+upload.get(0);
    }

    /**
     * 下载文件
     * @param fileName
     * @return
     */
    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        minioUtils.download(fileName, response);
    }

}
