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

    /**
     * poi-tl 根据模板生成wold
     */
    void createPoiTlTemplateWorld();

    /**
     * 导入Excel数据为JSON格式
     * @param file
     * @return
     * @throws Exception
     */
    JSONArray importToJSON(MultipartFile file) throws Exception;

    /**
     * 导入Excel数据为指定对象
     * @param file
     * @throws Exception
     */
    void importToVo(MultipartFile file) throws Exception;

    /**
     * 导入多sheet的Excel
     * @param file
     * @throws Exception
     */
    void importManySheet(MultipartFile file) throws Exception;

    /**
     * 导出Excel
     * @param response
     * @throws MalformedURLException
     */
    void export(HttpServletResponse response) throws MalformedURLException;

    /**
     * 导出指定对象的excel
     * @param response
     */
    void exportVo(HttpServletResponse response);

    /**
     * 导出多sheet的Excel
     * @param response
     */
    void exportManySheet(HttpServletResponse response);

}