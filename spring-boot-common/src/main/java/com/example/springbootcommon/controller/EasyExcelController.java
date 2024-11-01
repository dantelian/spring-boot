package com.example.springbootcommon.controller;

import com.example.springbootcommon.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * easyexcel
 */
@RestController
@RequestMapping("/easyExcel")
public class EasyExcelController {

    @Autowired
    private EasyExcelService easyExcelService;

    /**
     * easyexcel 导出多级表头
     */
    @GetMapping("/exportExcelMultistageHeader")
    public void exportExcelMultistageHeader(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelMultistageHeader(response);
    }


}
