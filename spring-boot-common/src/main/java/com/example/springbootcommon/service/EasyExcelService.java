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

}
