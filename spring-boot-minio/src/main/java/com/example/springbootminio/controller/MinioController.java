package com.example.springbootminio.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.example.springbootminio.common.utils.MinioUtils;
import com.example.springbootminio.model.entity.Attachments;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
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
    @PostMapping("/uploads")
    public Object uploads(MultipartFile[] file) {
        List<String> upload =  minioUtils.upload(file);
        return address+"/"+bucketName+"/"+upload.get(0);
    }

    @PostMapping("/upload")
    public Object upload(MultipartFile file, String fileType) {
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));

        if (!validateFileName(fileName)) {
            System.err.println("文件名不能带有#、+、&、?、%、=、/等特殊字符,请调整后重试！");
        }

        StringBuffer savePath = new StringBuffer("/");
        if (StringUtils.isNotBlank(fileType)) {
            savePath.append(fileType.startsWith("/") ? fileType.replaceFirst("/", "") : fileType)
                    .append("/")
                    .append(DateUtil.format(new Date(), "yyyy-MM-dd"))
                    .append("/");
        }

        String saveName = file.getOriginalFilename();
        String[] split = saveName.split("\\.");
        if (split.length > 1) {
            saveName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
        } else {
            saveName = saveName + System.currentTimeMillis();
        }

        String upload =  minioUtils.upload(file, savePath + saveName);

        Attachments attachment = new Attachments();
        attachment.setFileName(fileName);
        attachment.setFileFullName(file.getOriginalFilename());
        attachment.setFileType(fileType);
        attachment.setExt(ext);
        attachment.setPath(upload);
        attachment.setSaveName(saveName);
        attachment.setSize(FileUtil.readableFileSize(file.getSize()));
        System.out.println("attachment:" + attachment);

        return address + "/" + bucketName + upload;
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

    // 校验文件名是否携带特殊字符
    private Boolean validateFileName(String fileName) {
        Boolean flag = true;
        String[] strs = {"+", "%", "#", "&", "=", "?", "/"};
        for (int i = 0; i < strs.length; i++) {
            if (fileName.indexOf(strs[i]) >= 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
