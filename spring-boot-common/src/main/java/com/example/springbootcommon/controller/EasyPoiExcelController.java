package com.example.springbootcommon.controller;

import com.example.springbootcommon.model.easyPoi.User;
import com.example.springbootcommon.service.EasyPoiExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * easy-poi Excel
 */
@RestController
@RequestMapping("/easyPoiExcel")
public class EasyPoiExcelController {

    @Autowired
    EasyPoiExcelService easyPoiExcelService;

    /**
     * easy poi excel导出数据-根据实体
     */
    @GetMapping("/exportExcelClass")
    public void exportExcelClass(HttpServletResponse response) throws IOException {
        easyPoiExcelService.exportExcelClass(response);
    }

    /**
     * easy poi excel导入数据-根据实体
     */
    @PostMapping("/importExcelClass")
    public List<User> importExcelClass(@RequestParam("file") MultipartFile file) throws IOException {
        return easyPoiExcelService.importExcelClass(file);
    }



}
