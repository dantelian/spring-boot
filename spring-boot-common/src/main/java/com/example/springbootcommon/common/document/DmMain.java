package com.example.springbootcommon.common.document;

import com.example.springbootcommon.common.util.ApachePoiWordUtil;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DmMain {

    private static final String DB_URL = "jdbc:dm://**:5236?schema=HNYANCAO_ADMIN_DEV&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&useSSL=true&characterEncoding=UTF-8";
    private static final String DB_DATABASE = "HNYANCAO_ADMIN_DEV";
    private static final String DB_USERNAME = "**";
    private static final String DB_PASSWORD = "**";
    private static final String FILE_OUTPUT_DIR = "C:\\Users\\ddd\\Desktop\\java\\数据库设计文档.docx";

    private static Connection conn = null;

    public static void main(String[] args) {
        try {
            buildDoc();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void buildDoc() {
        try {
            conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Map<String, String>> tableList = getTableList();
        List<Map<String, String>> colsList = getColsList();

        // 创建word对象并设置页面布局
        XWPFDocument doc = new XWPFDocument();
        // 设置页面大小
        ApachePoiWordUtil.setPageSize(doc, 11907, 16840);
        // 设置页边距
        ApachePoiWordUtil.setPageMar(doc, 720, 1440, 720, 1440);
        // 文章题目
        ApachePoiWordUtil.createThemeParagraph(doc, "数据库设计文档", null);

        for (Map map : tableList) {
            buildItem(doc, map, colsList);
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(FILE_OUTPUT_DIR);
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
    }

    private static void buildItem(XWPFDocument doc, Map<String, String> map, List<Map<String, String>> colsList) {
        String tableName = map.get("tableName");
        ApachePoiWordUtil.createTitleParagraph(doc, "表名：" + tableName, 9, "宋体");
        ApachePoiWordUtil.createTextParagraph(doc, "说明：" + map.get("comments"), 9, "宋体");

        List<Map<String, String>> list = colsList.stream().filter(s -> tableName.equals(s.get("tableName"))).collect(Collectors.toList());
        XWPFTable infoTable = doc.createTable(list.size() + 1, 7);

        //设置表格样式
        Integer[] cW = {652, 1588, 2291, 1372, 2376, 2376, 1372}; // 指定每列宽度
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
                cellParagraphRun.setFontSize(9); // 字体大小

                CTTcPr ctTcPr = cellList.get(j).getCTTc().isSetTcPr() ? cellList.get(j).getCTTc().getTcPr() : cellList.get(j).getCTTc().addNewTcPr();
                CTTblWidth ctTblWidth = ctTcPr.addNewTcW();
                ctTblWidth.setType(STTblWidth.DXA);
                ctTblWidth.setW(BigInteger.valueOf(cW[j]));
                if(i == 0) {
                    cellParagraphRun.setFontFamily("宋体");
                    cellParagraphRun.setBold(true);
                } else {
                    cellParagraphRun.setFontFamily("宋体");
                }
            }
        }

        // 表格数据
        List<List<Object>> tableData = new ArrayList<List<Object>>();
        // 表格头
        List<Object> headerList = new ArrayList<Object>();
        headerList.add("序号");
        headerList.add("名称");
        headerList.add("数据类型");
        headerList.add("长度");
        headerList.add("允许空值");
        headerList.add("默认值");
        headerList.add("说明");
        tableData.add(headerList);
        // 表格内容
        for (int i = 0; i < list.size(); i++) {
            Map colsMap = list.get(i);

            List<Object> row = new ArrayList<Object>();
            row.add(1 + i);
            row.add(colsMap.get("columnName"));
            row.add(colsMap.get("dataType"));
            row.add(colsMap.get("dataLength"));
            row.add(colsMap.get("nullable"));
            row.add(colsMap.get("dataDefault"));
            row.add(colsMap.get("comments"));
            tableData.add(row);
        }

        // 往表格中填充数据
        ApachePoiWordUtil.fillTableData(infoTable, tableData);
    }

    private static List getTableList() {
        List<Map<String, String>> list = new ArrayList<>();

        String sql = "SELECT TABLE_NAME, COMMENTS FROM dba_tab_comments WHERE owner = '" + DB_DATABASE + "'";
        try {
            PreparedStatement selectps = conn.prepareStatement(sql);
            ResultSet rs = selectps.executeQuery();
            while(rs.next()){
                Map<String, String> map = new HashMap<>();
                map.put("tableName", rs.getString("TABLE_NAME"));
                map.put("comments", rs.getString("COMMENTS"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List getColsList() {
        List<Map<String, String>> list = new ArrayList<>();

        String sql = "SELECT cols.table_name, cols.COLUMN_NAME, cols.DATA_TYPE, cols.DATA_LENGTH, cols.NULLABLE, cols.DATA_DEFAULT, comts.COMMENTS\n" +
                "FROM all_tab_columns cols\n" +
                "LEFT JOIN dba_col_comments comts\n" +
                "ON cols.owner = comts.owner\n" +
                "AND cols.table_name = comts.table_name\n" +
                "AND cols.column_name = comts.column_name\n" +
                "WHERE cols.owner = '" + DB_DATABASE + "'";
        try {
            PreparedStatement selectps = conn.prepareStatement(sql);
            ResultSet rs = selectps.executeQuery();
            while(rs.next()){
                Map<String, String> map = new HashMap<>();
                map.put("tableName", rs.getString("TABLE_NAME"));
                map.put("columnName", rs.getString("COLUMN_NAME"));
                map.put("dataType", rs.getString("DATA_TYPE"));
                map.put("dataLength", rs.getString("DATA_LENGTH"));
                map.put("nullable", rs.getString("NULLABLE"));
                map.put("dataDefault", rs.getString("DATA_DEFAULT"));
                map.put("comments", rs.getString("COMMENTS"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



}
