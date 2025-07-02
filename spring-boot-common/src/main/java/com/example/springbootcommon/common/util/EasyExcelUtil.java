package com.example.springbootcommon.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.springbootcommon.common.easyExcel.CascadeSelectWriteHandler;
import com.example.springbootcommon.common.easyExcel.DownHandler;
import com.example.springbootcommon.common.easyExcel.SelectItem;
import com.example.springbootcommon.model.easyexcel.EasyExcelSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
public class EasyExcelUtil {

    /**
     * 多级表头导出Excel xlsx
     * @param fileName      文件名称
     * @param sheetName     工作表名称
     * @param head          多级表头
     * @param contentData   表内容
     * @param response
     * @throws IOException
     */
    public static void exportExcelMultistageHeader(String fileName, String sheetName, List<List<String>> head, List<List<Object>> contentData, HttpServletResponse response) throws IOException {
        /*response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName1 = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName1 + ".xlsx");
        ExcelWriter writer = EasyExcelFactory.write(response.getOutputStream())
                // 核心代码：表头和正文的样式在此
                .registerWriteHandler(getCellStyle()).build();

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
        writer.finish();*/

        EasyExcel.write(getOutputStream(fileName, response))
                .excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                .registerWriteHandler(getCellStyle())
                .head(head)
                .doWrite(contentData);
    }

    /**
     * sheet页导出.设置下拉框
     * @param fileName      文件名称
     * @param sheetName     工作表名称
     * @param contentData   表内容
     * @param dropDownMap   下拉内容
     * @param response
     * @param clazz
     * @throws Exception
     */
    public static void exportExcelSelect(String fileName, String sheetName, List<?> contentData, Map<Integer, String[]> dropDownMap, HttpServletResponse response, Class<?> clazz) throws IOException {
        EasyExcel.write(getOutputStream(fileName, response), clazz)
                .excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                .registerWriteHandler(new DownHandler(dropDownMap))
                .registerWriteHandler(getCellStyle()).doWrite(contentData);
    }

    /**
     * 级联下拉
     * @param fileName      文件名称
     * @param sheetName     工作表名称
     * @param contentData   表内容
     * @param selectItems   级联选项
     * @param response
     * @param clazz
     * @throws IOException
     */
    public static void exportExcelCascadeSelect(String fileName, String sheetName, List<?> contentData, List<SelectItem> selectItems, HttpServletResponse response, Class<?> clazz) throws IOException {
        EasyExcel.write(getOutputStream(fileName, response), clazz)
                .excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                .registerWriteHandler(new CascadeSelectWriteHandler(selectItems))
                .registerWriteHandler(getCellStyle()).doWrite(contentData);
    }

    public static void exportManySheet(HttpServletResponse response, String fileName, List<EasyExcelSheet> sheets) throws IOException {
        try {

            ExcelWriter excelWriter = EasyExcel.write(getOutputStream(fileName, response))
                    .excelType(ExcelTypeEnum.XLSX).build();
            sheets.forEach(sheet -> {
                // sheet页位置，sheet页名称
                WriteSheet writeSheet = EasyExcel.writerSheet(sheet.getSheetIndex(), sheet.getSheetName())
                        // 表头实体
                        .head(sheet.getHeadClass())
                        .registerWriteHandler(EasyExcelUtil.getCellStyle())
                        .build();
                excelWriter.write(sheet.getDataset(), writeSheet);
            });
            excelWriter.finish();
            response.flushBuffer();
        } catch (Exception e) {
            log.error("文件【{}】下载失败", fileName);
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 配置字体，表头背景等
     * @return
     */
    public static HorizontalCellStyleStrategy getCellStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // 灰色背景
        WriteFont headWriteFont = new WriteFont();
        // 加粗
        headWriteFont.setBold(true);
        headWriteFont.setFontHeightInPoints((short) 15); // 设置字号
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 设置字号
        contentWriteFont.setFontHeightInPoints((short) 14);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //导出数据垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //导出数据水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //边框
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 设置请求
     */
    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws IOException {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition",  "attachment;filename*=utf-8'zh_cn'" + fileName + ".xlsx");
        return response.getOutputStream();
    }

    public static <T> void validateEmpty(Class<?> clazz, T bean) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (ObjectUtils.isEmpty(field.get(bean))) {
                    // 获取字段上的注解
                    ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
                    if (annotation != null) {
                        if (annotation.value()[0].endsWith("*")) {
                            throw new RuntimeException("必填字段不能为空: " + annotation.value()[0]);
                        }
                    } else {
                        throw new RuntimeException("必填字段不能为空");
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
