package com.example.springbootcommon.model.vo;

import com.example.springbootcommon.common.office.ExcelExport;
import lombok.Data;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-10 16:16
 **/
@Data
public class TestExcelExportVo {

    @ExcelExport(value = "序号", sort = 1)
    private String num;

    @ExcelExport(value = "名称")
    private String name;

    @ExcelExport(value = "电话")
    private String phone;

    @ExcelExport(value = "性别", kv = "1-男;2-女")
    private Integer sex;

}
