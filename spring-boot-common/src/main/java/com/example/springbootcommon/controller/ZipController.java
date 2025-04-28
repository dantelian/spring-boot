package com.example.springbootcommon.controller;

import com.example.springbootcommon.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/zip")
public class ZipController {

    @Autowired
    private ZipService zipService;

    /**
     * 解压缩文件
     * @param sourceFile    压缩问及那
     * @param targetFolder  解压到指定目录
     */
    @GetMapping("/unzip")
    public List<String> unzip(String sourceFile, String targetFolder) throws IOException {
        return zipService.unzip(sourceFile, targetFolder);
    }

}
