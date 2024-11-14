package com.example.springbootcommon.model.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;

@Data
@AllArgsConstructor
@ColumnWidth(20)
@ContentRowHeight(100) // 设置行高
public class ImageModel implements Serializable {

    @ExcelProperty(value = "姓名")
    private String username;

    @ExcelProperty(value = "头像")
    private URL avatar;

}
