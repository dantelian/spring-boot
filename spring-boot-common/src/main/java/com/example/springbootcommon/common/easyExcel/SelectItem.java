package com.example.springbootcommon.common.easyExcel;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 下拉选项
 */
@Data
public class SelectItem {

    public SelectItem(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * 下拉框所在列的索引，从0开始
     */
    private Integer columnIndex;
    /**
     * 下拉框的值列表
     */
    private List<DataItem> dataItems;
    /**
     * 子级对应的下拉框数据
     */
    private SelectItem subSelect;

    public void addDataItem(String mappingKey, List<String> values) {
        if (this.dataItems == null) {
            this.dataItems = new ArrayList<>();
        }
        this.dataItems.add(new DataItem(mappingKey, values));
    }

    public void addDataItem(List<String> values) {
        this.addDataItem("_" + UUID.randomUUID().toString().replaceAll("-", ""), values);
    }

    @Data
    public static class DataItem {

        /**
         * 关联上级的key
         */
        private String mappingKey;
        /**
         * 当前下拉框的值
         */
        private List<String> values;
        /**
         * 当前下拉框的引用，隐藏页单元格地址
         */
        private String hiddenFormulaRef;

        public DataItem(String mappingKey, List<String> values) {
            Assert.hasLength(mappingKey, "mappingKey is not blank");
            Assert.notEmpty(values, "values is not empty");
            this.mappingKey = mappingKey;
            this.values = values;
        }

    }

}
