package com.example.springbootcommon.serviceImpl;

import com.example.springbootcommon.service.OfficeWordService;
import com.example.springbootcommon.util.OfficeWordUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-06 09:21
 **/
@Service
public class OfficeWordServiceImpl implements OfficeWordService {

    @Override
    public void buildApacheWord(HttpServletResponse response) {
        // 创建word对象并设置页面布局
        XWPFDocument doc = new XWPFDocument();
        // 设置页面大小
        OfficeWordUtil.setPageSize(doc, 11907, 16840);
        // 设置页边距
        OfficeWordUtil.setPageMar(doc, 720, 1440, 720, 1440);
        // 文章题目
        OfficeWordUtil.createThemeParagraph(doc, "文章题目", null);
        // 创建一级标题
        OfficeWordUtil.createTitleParagraph(doc, "一、一级标题", 18, "宋体");

        // 创建表格
        XWPFTable infoTable = doc.createTable(4, 6);
        OfficeWordUtil.setTblLayoutType(infoTable);
        // 设置表格总宽度与水平对齐方式
        OfficeWordUtil.setTableWidthAndHAlign(infoTable, "10575", STJc.CENTER);
        // 设置表格行高
        OfficeWordUtil.setTableHeight(infoTable, 560, STVerticalJc.CENTER);

        // 合并行（第一列的第一第二行）
        OfficeWordUtil.mergeCellsVertically(infoTable, 0, 0, 1);
        // 合并列（第三行的第二第三列）
        OfficeWordUtil.mergeCellsHorizontal(infoTable, 2, 1, 2);

        //设置表格样式
        Integer[] cW = {1588, 652, 2291, 1372, 2376, 2376}; // 指定每列宽度
        List<XWPFTableRow> rowList = infoTable.getRows();
        for(int i = 0; i < rowList.size(); i++) {
            XWPFTableRow infoTableRow = rowList.get(i);
            List<XWPFTableCell> cellList = infoTableRow.getTableCells();
            for(int j = 0; j < cellList.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                if (i == 0) { // 首行剧中
                    cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                } else { // 其他行靠左
                    cellParagraph.setAlignment(ParagraphAlignment.LEFT);
                }
                XWPFRun cellParagraphRun = cellParagraph.createRun();
                cellParagraphRun.setFontSize(12); // 字体大小

                CTTcPr ctTcPr = cellList.get(j).getCTTc().isSetTcPr() ? cellList.get(j).getCTTc().getTcPr() : cellList.get(j).getCTTc().addNewTcPr();
                CTTblWidth ctTblWidth = ctTcPr.addNewTcW();
                ctTblWidth.setType(STTblWidth.DXA);
                ctTblWidth.setW(BigInteger.valueOf(cW[j]));
                if(i == 0) {
                    cellParagraphRun.setFontFamily("宋体");
                    cellParagraphRun.setBold(true);
                } else {
                    cellParagraphRun.setFontFamily("仿宋");
                }
            }
        }

        // 表格数据
        List<List<Object>> tableData = new ArrayList<List<Object>>();
        // 表格头
        List<Object> headerList = new ArrayList<Object>();
        headerList.add("类别");
        headerList.add("序号");
        headerList.add("任务名称");
        headerList.add("任务状态");
        headerList.add("本周工作总结");
        headerList.add("下周工作计划");
        tableData.add(headerList);
        // 表格内容
        List<Object> row = new ArrayList<Object>();
        row.add(1);
        row.add(2);
        row.add(3);
        row.add(4);
        row.add(5);
        row.add(6);
        tableData.add(row);
        // 往表格中填充数据
        OfficeWordUtil.fillTableData(infoTable, tableData);

        // 插入图片
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/snow1.jpg");
        try {
            OfficeWordUtil.createPicParagraph(doc, inputStream, XWPFDocument.PICTURE_TYPE_JPEG, "snow1.jpg", 200, 200);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 保存到本地
        try {
            OutputStream os = new FileOutputStream("e:/wordWrite.docx");
            doc.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回 response
//        OutputStream outputStream = null;
//        try {
//            outputStream = new BufferedOutputStream(response.getOutputStream());
//            doc.write(outputStream);
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        System.out.println("end");
    }
}
