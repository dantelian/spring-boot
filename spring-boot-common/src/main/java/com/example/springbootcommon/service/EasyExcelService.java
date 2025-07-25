package com.example.springbootcommon.service;

import com.example.springbootcommon.model.easyexcel.UserModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EasyExcelService {

    /**
     * easyexcel 导出多级表头
     * @param response
     * @throws IOException
     */
    void exportExcelMultistageHeader(HttpServletResponse response) throws IOException;

    /**
     * easyexcel 导出下拉
     * @param response
     * @throws IOException
     */
    void exportExcelSelect(HttpServletResponse response) throws IOException;

    /**
     * easyexcel 导出级联下拉
     * @param response
     * @throws IOException
     */
    void exportExcelCascadeSelect(HttpServletResponse response) throws IOException;

    /**
     * easyexcel 导出图片
     * @param response
     * @throws IOException
     */
    void exportExcelImage(HttpServletResponse response) throws IOException;

    /**
     * 导出-根据模板
     * @param response
     * @throws IOException
     */
    void exportExcelTemplate(HttpServletResponse response) throws IOException;

    /**
     * 导出-根据模板-根据参数填充
     * @param response
     * @throws IOException
     */
    void exportExcelParamsTemplate(HttpServletResponse response) throws IOException;

    /**
     * 导出-导出多 Sheet 页Excel
     * @param response
     * @throws IOException
     */
    void exportManySheet(HttpServletResponse response) throws IOException;

    List<UserModel> importExcelClass(MultipartFile file) throws IOException;

    Object importManySheet(MultipartFile file) throws IOException;

    Object importToJSON(MultipartFile file) throws Exception;

}
