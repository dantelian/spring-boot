package com.example.springbootcommon.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.springbootcommon.common.util.ApachePoiExcelUtils;
import com.example.springbootcommon.model.apachePoi.ApachePoiExcelExportVo;
import com.example.springbootcommon.service.ApachePoiExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * apache-poi Excel
 */
@RestController
@RequestMapping("/apachePoiExcel")
public class ApachePoiExcelController {

    @Autowired
    private ApachePoiExcelService apachePoiExcelService;

    /**
     * apache-poi 导出excel
     * 数据格式Map
     * 支持下拉导出
     * @param response
     */
    @GetMapping("/exportMap")
    public void exportMap(HttpServletResponse response) throws MalformedURLException {
        apachePoiExcelService.exportMap(response);
    }

    /**
     * apache-poi 导出excel-根据对象导出
     * @param response
     */
    @GetMapping("/exportVo")
    public void exportVo(HttpServletResponse response) {
        apachePoiExcelService.exportVo(response);
    }

    /**
     * apache-poi 导出excel模板
     * @param response
     */
    @GetMapping("/exportModel")
    public void exportModel(HttpServletResponse response) {
        // 导出模板不包含样本数据
//        ExcelUtils.exportTemplate(response, "测试模板", TestExcelExportVo.class);

        // 导出模板包含样本数据
        ApachePoiExcelUtils.exportTemplate(response, "测试模板", ApachePoiExcelExportVo.class, true);
    }

    /**
     * apache-poi 导出多 Sheet 页Excel
     */
    @GetMapping("/exportManySheet")
    public void exportManySheet(HttpServletResponse response) {
        apachePoiExcelService.exportManySheet(response);
    }

    /**
     * apache-poi 导出级联下拉 方式一
     */
    @GetMapping("/exportExcelCascadeSelect")
    public void exportExcelCascadeSelect(HttpServletResponse response) throws IOException {
        apachePoiExcelService.exportExcelCascadeSelect(response);
    }

    /**
     * apache-poi 导出级联下拉 方式二
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcelCascadeSelect2")
    public void exportExcelCascadeSelect2(HttpServletResponse response) throws IOException {
        apachePoiExcelService.exportExcelCascadeSelect2(response);
    }


    /**
     * apache-poi 导入excel数据
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importToJSON")
    public JSONArray importToJSON(@RequestPart("file") MultipartFile file) throws Exception {
        return apachePoiExcelService.importToJSON(file);
    }

    /**
     * apache-poi 导入excel数据并转换为特定对象
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importToVo")
    public void importToVo(@RequestPart("file") MultipartFile file) throws Exception {
        apachePoiExcelService.importToVo(file);
    }

    /**
     * apache-poi 导入多Sheet页
     * @param file
     * @throws Exception
     */
    @PostMapping("/importManySheet")
    public void importManySheet(@RequestPart("file") MultipartFile file) throws Exception {
        apachePoiExcelService.importManySheet(file);
    }

}
