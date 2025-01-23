package com.example.springbootcommon.model.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.example.springbootcommon.common.easyExcel.DropDown;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

//    @ExcelProperty(value = "时间", format = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date time;

}
