package com.example.springbootcommon.common.easyExcel;

import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.Row;

/**
 * 自定义行高
 */
public class CustomRowHeightStyleStrategy extends AbstractRowHeightStyleStrategy {

    /**
     * 设置特殊处理的行号
     */
    Integer contentHeight;

    public CustomRowHeightStyleStrategy(Integer contentHeight) {
        this.contentHeight = contentHeight;
    }

    /**
     * 设置表头的行高
     */
    @Override
    protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
        //默认表头高度
        row.setHeightInPoints((20));
    }

    /**
     * 设置内容的行高
     */
    @Override
    protected void setContentColumnHeight(Row row, int relativeRowIndex) {
        //默认主体的高度
        row.setHeightInPoints(contentHeight);

    }
}
