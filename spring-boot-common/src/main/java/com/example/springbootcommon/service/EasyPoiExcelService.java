package com.example.springbootcommon.service;

import com.example.springbootcommon.model.easyPoi.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EasyPoiExcelService {

    /**
     * easy poi excel导出数据-根据实体
     * @param response
     */
    void exportExcelClass(HttpServletResponse response) throws IOException;

    /**
     * easy poi excel导入数据-根据实体
     * @param file
     * @throws IOException
     */
    List<User> importExcelClass(MultipartFile file) throws IOException;

}
