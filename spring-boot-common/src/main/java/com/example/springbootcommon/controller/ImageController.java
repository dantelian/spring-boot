package com.example.springbootcommon.controller;

import com.example.springbootcommon.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 压缩图片
     * @param file
     * @param response
     */
    @PostMapping("/compressImage")
    public void compressImage(@RequestPart("file") MultipartFile file, HttpServletResponse response) throws IOException {
        imageService.compressImage(file, response);
    }


}
