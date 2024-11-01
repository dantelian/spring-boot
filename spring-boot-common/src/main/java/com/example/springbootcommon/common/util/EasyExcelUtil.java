package com.example.springbootcommon.common.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class EasyExcelUtil {

    /**
     * 多级表头导出Excel xlsx
     * @param fileNmae      文件名称
     * @param sheetName     工作表名称
     * @param head          多级表头
     * @param contentData   表内容
     * @param response
     * @throws IOException
     */
    public static void exportMultistageHeaderExcel(String fileNmae, String sheetName, List<List<String>> head, List<List<Object>> contentData, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(fileNmae, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        ExcelWriter writer = EasyExcelFactory.write(response.getOutputStream())
                // 核心代码：表头和正文的样式在此
                .registerWriteHandler(setConfigure()).build();

        // 动态添加表头，适用一些表头动态变化的场景
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetName(sheetName);
        sheet1.setSheetNo(0);

        // 创建一个表格，用于 Sheet 中使用
        WriteTable table = new WriteTable();
        table.setTableNo(1);
        // 核心代码：设置表头
        table.setHead(head);
        // 写数据
        writer.write(contentData, sheet1, table);
        writer.finish();
    }

    //配置字体，表头背景等
    private static HorizontalCellStyleStrategy setConfigure() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // 黄色背景
        WriteFont headWriteFont = new WriteFont();
        // 加粗
        headWriteFont.setBold(true);
        headWriteFont.setFontHeightInPoints((short) 15); // 设置行高
        headWriteCellStyle.setWriteFont(headWriteFont);


        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
//        contentWriteFont.setFontHeightInPoints((short) 14);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //边框
        //导出数据垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //导出数据水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

}
