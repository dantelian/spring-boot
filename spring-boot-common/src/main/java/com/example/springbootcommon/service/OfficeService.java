package com.example.springbootcommon.service;


import com.alibaba.fastjson.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-06 09:19
 **/
public interface OfficeService {

    /**
     * apache.poi 生成word文件
     */
    void buildApacheWord(HttpServletResponse response);

    void createPoiTlTemplateWorld();

    JSONArray importToJSON(MultipartFile file) throws Exception;

    void importToVo(MultipartFile file) throws Exception;

    void importManySheet(MultipartFile file) throws Exception;

    void export(HttpServletResponse response) throws MalformedURLException;

    void exportVo(HttpServletResponse response);

    void exportManySheet(HttpServletResponse response);

}
