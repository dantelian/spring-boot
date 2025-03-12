package com.example.springbootcommon.common.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;

public class EmptyRowFilterListener<T> extends AnalysisEventListener<T> {
    private List<T> filteredData = new ArrayList<>();

    @Override
    public void invoke(T data, AnalysisContext context) {
        // 检查数据是否为空（根据你的实际情况定义何为“空”）
        boolean isEmpty = true; // 根据实际情况调整判断逻辑
        // 例如，如果data是Map类型，可以这样检查：
        // Map<Integer, String> mapData = (Map<Integer, String>) data;
        // for (String value : mapData.values()) {
        //     if (value != null && !value.trim().isEmpty()) {
        //         isEmpty = false;
        //         break;
        //     }
        // }
        if (!isEmpty) { // 或者根据你的实际情况调整条件判断逻辑
            filteredData.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理过滤后的数据列表
        System.out.println("Filtered data size: " + filteredData.size());
    }
}
