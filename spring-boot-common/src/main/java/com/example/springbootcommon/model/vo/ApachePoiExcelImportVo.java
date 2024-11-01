package com.example.springbootcommon.model.vo;

import com.example.springbootcommon.common.apachePoi.ExcelImport;
import lombok.Data;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-10 15:32
 **/
@Data
public class ApachePoiExcelImportVo {

    // 行号
    private Integer rowNum;
    // 行原始数据
    private String rowData;
    // 错误提示
    private String rowTips;

    @ExcelImport("序号")
    private String num;

    @ExcelImport(value = "名称", required = true)
    private String name;

    @ExcelImport(value = "电话", maxLength = 11)
    private String phone;

    @ExcelImport(value = "性别", kv = "1-男;2-女")
    private Integer sex;

}
