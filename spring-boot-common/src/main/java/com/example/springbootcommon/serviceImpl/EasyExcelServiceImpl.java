package com.example.springbootcommon.serviceImpl;

import com.alibaba.excel.util.ListUtils;
import com.example.springbootcommon.common.util.EasyExcelUtil;
import com.example.springbootcommon.service.EasyExcelService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class EasyExcelServiceImpl implements EasyExcelService {

    @Override
    public void exportExcelMultistageHeader(HttpServletResponse response) throws IOException {
        List<List<String>> headTitles = ListUtils.newArrayList();
        //表头可以根据实际情况进行修改, 相邻列同名自动合并
        headTitles.add(ListUtils.newArrayList( "编号"));
        headTitles.add(ListUtils.newArrayList( "分类一", "金额"));
        headTitles.add(ListUtils.newArrayList( "分类一", "日期"));
        headTitles.add(ListUtils.newArrayList( "分类一", "数目"));
        headTitles.add(ListUtils.newArrayList( "分类二", "金额"));
        headTitles.add(ListUtils.newArrayList( "分类二", "日期"));
        headTitles.add(ListUtils.newArrayList( "分类二", "数目"));
        headTitles.add(ListUtils.newArrayList("长度"));

        List<List<Object>> contentList = ListUtils.newArrayList();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        //数据根据实际获取进行填充
        contentList.add(ListUtils.newArrayList("0001", "190.76", "2024-05-10", "12", "6.78", "2024-02-15","11","183.97"));
        contentList.add(ListUtils.newArrayList("0001", "190.77", "2024-05-10", "13", "6.88", "2024-02-15","14","183.97"));

        EasyExcelUtil.exportMultistageHeaderExcel("fileName", "sheetName", headTitles, contentList, response);
    }

}
