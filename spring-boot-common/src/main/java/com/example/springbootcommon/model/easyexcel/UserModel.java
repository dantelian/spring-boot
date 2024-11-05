package com.example.springbootcommon.model.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.example.springbootcommon.common.easyExcel.DropDown;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
@ColumnWidth(20)
public class UserModel implements Serializable {

    @ExcelProperty(value = "序号", index = 0)
    private Integer index;

    @ExcelProperty(value = "姓名", index = 1)
    private String username;

    @DropDown(value = {"男", "女"}) // 导出下拉 需使用：DownHandler
    @ExcelProperty(value = "性别", index = 2)
    private String gender;

    @ExcelProperty(value = "电话", index = 3)
    private String phone;

    @ExcelProperty(value = "角色", index = 4)
    private String role;

    // 忽略字段
    @ExcelIgnore
    private String ignore;

}
