package com.example.springbootcommon.controller;

import com.example.springbootcommon.model.easyexcel.UserModel;
import com.example.springbootcommon.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    /**
     * easyexcel 导出下拉
     */
    @GetMapping("/exportExcelSelect")
    public void exportExcelSelect(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelSelect(response);
    }

    /**
     * easyexcel 导出级联下拉
     */
    @GetMapping("/exportExcelCascadeSelect")
    public void exportExcelCascadeSelect(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelCascadeSelect(response);
    }

    /**
     * easyexcel 导出图片
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcelImage")
    public void exportExcelImage(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelImage(response);
    }

    /**
     * easyexcel 导出-根据模板
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelTemplate(response);
    }

    /**
     * easyexcel 导出-根据模板-根据参数填充
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcelParamsTemplate")
    public void exportExcelParamsTemplate(HttpServletResponse response) throws IOException {
        easyExcelService.exportExcelParamsTemplate(response);
    }


    /**
     * easyexcel导入数据-根据实体
     */
    @PostMapping("/importExcelClass")
    public List<UserModel> importExcelClass(@RequestParam("file") MultipartFile file) throws IOException {
        return easyExcelService.importExcelClass(file);
    }

}
