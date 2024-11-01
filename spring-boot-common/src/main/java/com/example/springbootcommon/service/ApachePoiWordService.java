package com.example.springbootcommon.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ApachePoiWordService {

    /**
     * apache.poi 生成 word文件
     * @param response
     */
    void buildApacheWord(HttpServletResponse response);

    /**
     * apache-poi poi-tl 根据模板生成 word 文件
     * @param response
     */
    void buildPoiTlTemplateWorld(HttpServletResponse response);

    /**
     * apache-poi word 转 pdf
     * 仅docx格式
     * @param file
     * @param response
     */
    void word2pdf(MultipartFile file, HttpServletResponse response);

}
