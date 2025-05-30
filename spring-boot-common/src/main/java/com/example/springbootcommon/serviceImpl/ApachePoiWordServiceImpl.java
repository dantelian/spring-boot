package com.example.springbootcommon.serviceImpl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.example.springbootcommon.common.util.ApachePoiWordUtil;
import com.example.springbootcommon.service.ApachePoiWordService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ApachePoiWordServiceImpl implements ApachePoiWordService {

    @Override
    public void buildApacheWord(HttpServletResponse response) {
        // 创建word对象并设置页面布局
        XWPFDocument doc = new XWPFDocument();
        // 设置页面大小
        ApachePoiWordUtil.setPageSize(doc, 11907, 16840);
        // 设置页边距
        ApachePoiWordUtil.setPageMar(doc, 720, 1440, 720, 1440);
        // 文章题目
        ApachePoiWordUtil.createThemeParagraph(doc, "文章题目", null);
        // 创建一级标题
        ApachePoiWordUtil.createTitleParagraph(doc, "一、一级标题", 18, "宋体");

        // 创建表格
        XWPFTable infoTable = doc.createTable(4, 6);
        ApachePoiWordUtil.setTblLayoutType(infoTable);
        // 设置表格总宽度与水平对齐方式
        ApachePoiWordUtil.setTableWidthAndHAlign(infoTable, "10575", STJc.CENTER);
        // 设置表格行高
        ApachePoiWordUtil.setTableHeight(infoTable, 560, STVerticalJc.CENTER);

        // 合并行（第一列的第一第二行）
        ApachePoiWordUtil.mergeCellsVertically(infoTable, 0, 0, 1);
        // 合并列（第三行的第二第三列）
        ApachePoiWordUtil.mergeCellsHorizontal(infoTable, 2, 1, 2);

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
        ApachePoiWordUtil.fillTableData(infoTable, tableData);

        // 插入图片
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/snow1.jpg");
        try {
            String filename = "snow1.jpg";
            ApachePoiWordUtil.createPicParagraph(doc, inputStream, XWPFDocument.PICTURE_TYPE_JPEG, filename, 200, 200);
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

        // 设置水印
        // 方式一
        ApachePoiWordUtil.setWordWaterMark(doc, "机密", "#d8d8d8");
        // 方式二
//        WordUtil.makeFullWaterMarkByWordArt(doc, "机密", "#888888", "0.7pt","-30");

        // 保存到本地
//        OutputStream os = null;
//        try {
//            os = new FileOutputStream("e:/wordWrite.docx");
//            doc.write(os);
//            os.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                os.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // 返回 response
        String filename = "wordWrite.docx";
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            doc.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("end");
    }

    @Override
    public void buildPoiTlTemplateWorld(HttpServletResponse response) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/snow1.jpg");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/apache_poi_temp.docx");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "夏弥");
        map.put("sex", "女");
        map.put("national", "汉族");
        map.put("address", "XXXXaddress");
//        map.put("author", Texts.of("Liziba").color("000000").create());
//        map.put("link", Texts.of("百度").link("https://baidu.com").create());
//        map.put("anchor", Texts.of("anchortxt").anchor("appendix1").create());

        // 图片 针对网络图片、SVG图片、Java图片都有处理
        // 方法一、图片路径（原大小）
//        String picPath =  "D:\\poi-tl\\pic.jpg";
//        map.put("header", picPath);
        // 方法二、指定图片大小
//        map.put("header", Pictures.ofLocal(picPath).size(420,350).center().create());
        // 方法三、图片流
        map.put("header", Pictures.ofStream(inputStream, PictureType.JPEG).size(420,350).create());

        // 表格
        // 表头
        RowRenderData tableHead = Rows.of("姓名", "性别", "地址", "微信公众号").center().bgColor("4472c4").create();
        // 第一行
        RowRenderData row1 = Rows.create("张三", "男", "广东深圳", "liziba_98");
        // 第二行
        RowRenderData row2 = Rows.create("李四", "男", "广东深圳", "liziba_98");
        // 合并第一行和第二行的第二列与第三列
        MergeCellRule rule = MergeCellRule.builder().map(MergeCellRule.Grid.of(1, 1), MergeCellRule.Grid.of(2, 1))
                .map(MergeCellRule.Grid.of(1, 2), MergeCellRule.Grid.of(2, 2)).build();
        map.put("table", Tables.of(tableHead, row1, row2).mergeRule(rule).center().create());

        XWPFTemplate template = XWPFTemplate.compile(is).render(map);
        // 保存到本地
        /*OutputStream os = null;
        try {
            os = new FileOutputStream("e:/wordTemplateWrite.docx");
            template.writeAndClose(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        // 返回 response
        String filename = "wordTemplateWrite.docx";
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            template.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void word2pdf(MultipartFile file, HttpServletResponse response) {

        // 本地转换
        /*try {
            FileInputStream fileInputStream = new FileInputStream("G:\\测试\\测试.docx");
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            PdfOptions pdfOptions = PdfOptions.create();
            FileOutputStream fileOutputStream = new FileOutputStream("G:\\poi笔记.pdf");
            PdfConverter.getInstance().convert(xwpfDocument,fileOutputStream,pdfOptions);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 返回 response
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            String filename = "word2pdf.pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            outputStream = new BufferedOutputStream(response.getOutputStream());
            inputStream = file.getInputStream();
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            PdfOptions pdfOptions = PdfOptions.create();
            PdfConverter.getInstance().convert(xwpfDocument, outputStream, pdfOptions);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
