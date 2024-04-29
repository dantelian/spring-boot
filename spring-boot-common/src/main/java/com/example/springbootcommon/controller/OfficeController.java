package com.example.springbootcommon.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.springbootcommon.common.util.ExcelUtils;
import com.example.springbootcommon.model.entity.User;
import com.example.springbootcommon.model.vo.TestExcelExportVo;
import com.example.springbootcommon.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-10 15:24
 **/
@RestController
@RequestMapping("/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    /**
     * poi 导入excel数据
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importToJSON")
    public JSONArray importToJSON(@RequestPart("file") MultipartFile file) throws Exception {
        return officeService.importToJSON(file);
    }

    /**
     * poi 导入excel数据并转换为特定对象
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importToVo")
    public void importToVo(@RequestPart("file") MultipartFile file) throws Exception {
        officeService.importToVo(file);
    }

    /**
     * poi 导入多Sheet页
     * @param file
     * @throws Exception
     */
    @PostMapping("/importManySheet")
    public void importManySheet(@RequestPart("file") MultipartFile file) throws Exception {
        officeService.importManySheet(file);
    }

    /**
     * poi 导出excel
     * @param response
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws MalformedURLException {
        officeService.export(response);
    }

    /**
     * poi 导出excel模板
     * @param response
     */
    @GetMapping("/exportModel")
    public void exportModel(HttpServletResponse response) throws MalformedURLException {
//        ExcelUtils.exportTemplate(response, "测试模板", TestExcelExportVo.class);

        // 导出模板包含样本数据
        ExcelUtils.exportTemplate(response, "测试模板", TestExcelExportVo.class, true);
    }

    /**
     * poi 导出excel-根据对象导出
     * @param response
     */
    @GetMapping("/exportVo")
    public void exportVo(HttpServletResponse response) throws MalformedURLException {
        officeService.exportVo(response);
    }

    /**
     * poi 导出多 Sheet 页Excel
     */
    @GetMapping("/exportManySheet")
    public void exportManySheet(HttpServletResponse response) {
        officeService.exportManySheet(response);
    }

    /**
     * word 转 pdf
     */
    @PostMapping("/word2pdf")
    public void wold2pdf(@RequestPart("file") MultipartFile file, HttpServletResponse response) {
        officeService.word2pdf(file, response);
    }

    /**
     * easy poi excel导入数据
     */
    @PostMapping("/importEasyPoiExcel")
    public List<User> importEasyPoiExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return officeService.importEasyPoiExcel(file);
    }

    /**
     * easy poi excel导出数据
     */
    @GetMapping("/exportEasyPoiExcel")
    public void exportEasyPoiExcel(HttpServletResponse response) throws IOException {
        officeService.exportEasyPoiExcel(response);
    }

    /**
     * easy poi excel 导出下拉 级联下拉
     */
    @GetMapping("/exportEasyPoiExcelSelect")
    public void exportEasyPoiExcelSelect(HttpServletResponse response) throws IOException {
        officeService.exportEasyPoiExcelSelect(response);
    }

}
