package com.example.springbootcommon.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.example.springbootcommon.common.util.ExcelUtils;
import com.example.springbootcommon.model.vo.TestExcelExportVo;
import com.example.springbootcommon.model.vo.TestExcelImportVo;
import com.example.springbootcommon.service.OfficeService;
import com.example.springbootcommon.common.util.WordUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-06 09:21
 **/
@Service
public class OfficeServiceImpl implements OfficeService {

    @Override
    public void buildApacheWord(HttpServletResponse response) {
        // 创建word对象并设置页面布局
        XWPFDocument doc = new XWPFDocument();
        // 设置页面大小
        WordUtil.setPageSize(doc, 11907, 16840);
        // 设置页边距
        WordUtil.setPageMar(doc, 720, 1440, 720, 1440);
        // 文章题目
        WordUtil.createThemeParagraph(doc, "文章题目", null);
        // 创建一级标题
        WordUtil.createTitleParagraph(doc, "一、一级标题", 18, "宋体");

        // 创建表格
        XWPFTable infoTable = doc.createTable(4, 6);
        WordUtil.setTblLayoutType(infoTable);
        // 设置表格总宽度与水平对齐方式
        WordUtil.setTableWidthAndHAlign(infoTable, "10575", STJc.CENTER);
        // 设置表格行高
        WordUtil.setTableHeight(infoTable, 560, STVerticalJc.CENTER);

        // 合并行（第一列的第一第二行）
        WordUtil.mergeCellsVertically(infoTable, 0, 0, 1);
        // 合并列（第三行的第二第三列）
        WordUtil.mergeCellsHorizontal(infoTable, 2, 1, 2);

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
        WordUtil.fillTableData(infoTable, tableData);

        // 插入图片
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/snow1.jpg");
        try {
            String filename = "snow1.jpg";
            WordUtil.createPicParagraph(doc, inputStream, XWPFDocument.PICTURE_TYPE_JPEG, filename, 200, 200);
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
        OutputStream os = null;
        try {
            os = new FileOutputStream("e:/wordWrite.docx");
            doc.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @Override
    public void createPoiTlTemplateWorld() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/snow1.jpg");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("templates/apache_poi_temp.docx");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "段然涛");
        map.put("sex", "男");
        map.put("national", "汉族");
        map.put("address", "许昌");
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
        OutputStream os = null;
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
        }
    }

    @Override
    public JSONArray importToJSON(MultipartFile file) throws Exception {
        JSONArray array = ExcelUtils.readMultipartFile(file);
        System.out.println("导入数据为:" + array);
        return array;
    }

    @Override
    public void importToVo(MultipartFile file) throws Exception {
        List<TestExcelImportVo> list = ExcelUtils.readMultipartFile(file, TestExcelImportVo.class);
        for (TestExcelImportVo vo : list) {
            System.out.println(vo.toString());
        }
    }

    @Override
    public void importManySheet(MultipartFile file) throws Exception {
        Map<String, JSONArray> map = ExcelUtils.readFileManySheet(file);
        map.forEach((key, value) -> {
            System.out.println("Sheet名称：" + key);
            System.out.println("Sheet数据：" + value);
            System.out.println("----------------------");
        });
    }

    @Override
    public void export(HttpServletResponse response) throws MalformedURLException {
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄","性别","头像", "城市", ExcelUtils.COLUMN_MERGE); // ExcelUtils.COLUMN_MERGE 合并列
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("诸葛亮");
        user1.add(60);
        user1.add("男");
        user1.add(new URL("https://profile.csdnimg.cn/A/7/3/3_sunnyzyq"));
        user1.add("北京");
        user1.add("合并");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(28);
        user2.add("女");
        user2.add(new URL("https://profile.csdnimg.cn/6/1/9/0_m0_48717371"));
        user2.add("上海");
        user2.add(ExcelUtils.ROW_MERGE); // 合并行
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        // 导出数据： 方案一
//        ExcelUtils.export(response,"用户表", sheetDataList);

        // 导出数据： 方案二 可设置下拉列表
        // 设置下拉列表（键为第几列（从0开始），值为下列数据）
        Map<Integer, List<String>> selectMap = new HashMap<>(1);
        selectMap.put(2, Arrays.asList("男", "女"));
        selectMap.put(4, Arrays.asList("北京", "上海"));
        ExcelUtils.export(response,"用户表", sheetDataList, selectMap);
    }

    @Override
    public void exportVo(HttpServletResponse response) {
        List<TestExcelExportVo> list = new ArrayList<>();
        TestExcelExportVo test1 = new TestExcelExportVo();
        test1.setNum("1");
        test1.setName("aa");
        test1.setPhone("aa");
        test1.setSex(1);
        list.add(test1);

        ExcelUtils.export(response, "测试", list, TestExcelExportVo.class);
    }

    @Override
    public void exportManySheet(HttpServletResponse response) {
        // 第 1 个 Sheet 页
        List<List<Object>> sheet1 = new ArrayList<>();
        List<Object> sheet1Head = new ArrayList<>();
        sheet1Head.add("姓名");
        sheet1Head.add("数学");
        sheet1Head.add("英语");
        sheet1.add(sheet1Head);
        List<Object> row1 = new ArrayList<>();
        row1.add("Jack");
        row1.add(85);
        row1.add(100);
        sheet1.add(row1);
        List<Object> row2 = new ArrayList<>();
        row2.add("Marry");
        row2.add(85);
        row2.add(100);
        sheet1.add(row2);
        // 第 2 个 Sheet 页
        List<List<Object>> sheet2 = new ArrayList<>();
        List<Object> sheet2Head = new ArrayList<>();
        sheet2Head.add("姓名");
        sheet2Head.add("音乐");
        sheet2Head.add("美术");
        sheet2.add(sheet2Head);
        List<Object> row01 = new ArrayList<>();
        row01.add("Jack");
        row01.add(77);
        row01.add(66);
        sheet2.add(row01);
        List<Object> row02 = new ArrayList<>();
        row02.add("Marry");
        row02.add(99);
        row02.add(88);
        sheet2.add(row02);
        // 将两个 Sheet 页添加到集合中
        Map<String, List<List<Object>>> sheets = new LinkedHashMap<>();
        sheets.put("文化课", sheet1);
        sheets.put("艺术课", sheet2);
        // 导出数据
        ExcelUtils.exportManySheet(response, "学生成绩表", sheets);
    }
}