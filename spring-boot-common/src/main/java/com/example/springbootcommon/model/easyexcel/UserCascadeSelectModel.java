package com.example.springbootcommon.model.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.springbootcommon.common.easyExcel.DropDown;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserCascadeSelectModel implements Serializable {

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

    @ExcelProperty(value = "省", index = 5)
    private String province;

    @ExcelProperty(value = "市", index = 6)
    private String city;

    @ExcelProperty(value = "区", index = 7)
    private String area;

    public UserCascadeSelectModel(Integer index, String username, String gender, String phone, String role) {
        this.index = index;
        this.username = username;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
    }
}
