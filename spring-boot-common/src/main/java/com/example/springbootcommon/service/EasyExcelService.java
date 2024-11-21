package com.example.springbootcommon.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

}
