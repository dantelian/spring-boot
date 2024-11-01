package com.example.springbootcommon.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ImageService {

    /**
     * 压缩图片
     * @param file
     * @param response
     */
    void compressImage(MultipartFile file, HttpServletResponse response) throws IOException;

}
