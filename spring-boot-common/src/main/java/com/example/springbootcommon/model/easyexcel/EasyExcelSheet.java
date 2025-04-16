package com.example.springbootcommon.model.easyexcel;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class EasyExcelSheet {

    private Integer sheetIndex;

    private String sheetName;

    private Class headClass;

    private Collection<?> dataset;

}
