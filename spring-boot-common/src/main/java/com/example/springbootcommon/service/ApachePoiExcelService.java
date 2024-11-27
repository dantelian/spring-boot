package com.example.springbootcommon.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

public interface ApachePoiExcelService {

    /**
     * apache-poi 导出Excel
     * 数据格式Map
     * 支持下拉导出
     * @param response
     * @throws MalformedURLException
     */
    void exportMap(HttpServletResponse response) throws MalformedURLException;

    /**
     * apache-poi 导出指定对象的excel
     * @param response
     */
    void exportVo(HttpServletResponse response);

    /**
     * apache-poi 导出多sheet的Excel
     * @param response
     */
    void exportManySheet(HttpServletResponse response);

    /**
     * apache-poi 导出级联下拉
     * @param response
     */
    void exportExcelCascadeSelect(HttpServletResponse response);

    /**
     * apache-poi 导出级联下拉
     * @param response
     */
    void exportExcelCascadeSelect2(HttpServletResponse response) throws IOException;


    /**
     * apache-poi 导入Excel数据为JSON格式
     * @param file
     * @return
     * @throws Exception
     */
    JSONArray importToJSON(MultipartFile file) throws Exception;

    /**
     * apache-poi 导入Excel数据为指定对象
     * @param file
     * @throws Exception
     */
    void importToVo(MultipartFile file) throws Exception;

    /**
     * apache-poi 导入多sheet的Excel
     * @param file
     * @throws Exception
     */
    void importManySheet(MultipartFile file) throws Exception;

}
