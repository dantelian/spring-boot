package com.example.springbootcommon.common.document;

import com.example.springbootcommon.common.util.ApachePoiWordUtil;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

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

public class MysqlScrewMain2 {
    private static final String DB_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://10.150.1.15:3306/db_dev?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";
    private static final String DB_DATABASE = "gxhj_hr_dev";

    private static final String FILE_OUTPUT_DIR = "C:\\Users\\ddd-z\\Desktop\\数据库设计文档.docx";

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
//        ApachePoiWordUtil.setPageSize(doc, 11907, 16840);
        ApachePoiWordUtil.setPageSize(doc, 12242, 15842);
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
        ApachePoiWordUtil.createTextParagraph(doc, tableName + "   " + map.get("comments"), 12, "宋体");

        List<Map<String, String>> list = colsList.stream().filter(s -> tableName.equals(s.get("tableName"))).collect(Collectors.toList());
        XWPFTable infoTable = doc.createTable(list.size() + 1, 9);

        //设置表格样式
//        Integer[] cW = {652, 1588, 2291, 1372, 2376, 652, 652, 2376, 1372}; // 指定每列宽度
        Integer[] cW = {567,2121,1134,567,567,567,567,787,1701}; // 指定每列宽度
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
                if(j == 0 || j == 1 || j == 2 || j == 3 || j == 7) {
                    cellParagraphRun.setFontFamily("Times New Roman");
//                    cellParagraphRun.setBold(true);
                } else {
                    cellParagraphRun.setFontFamily("宋体");
                }
            }
        }

        CTTblPr tblPr = infoTable.getCTTbl().getTblPr() == null ? infoTable.getCTTbl().addNewTblPr() : infoTable.getCTTbl().getTblPr();
        CTTblLayoutType t = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
        t.setType(STTblLayoutType.FIXED); // 使布局固定，不随内容改变宽度

        // 表格数据
        List<List<Object>> tableData = new ArrayList<List<Object>>();
        // 表格头
        List<Object> headerList = new ArrayList<Object>();
        headerList.add("序号");
        headerList.add("列名");
        headerList.add("数据类型");
        headerList.add("长度");
        headerList.add("主键");
        headerList.add("自增");
        headerList.add("允许空");
        headerList.add("默认值");
        headerList.add("列说明");
        tableData.add(headerList);
        // 表格内容
        for (int i = 0; i < list.size(); i++) {
            Map colsMap = list.get(i);

            List<Object> row = new ArrayList<Object>();
            row.add(1 + i);
            row.add(colsMap.get("columnName"));
            row.add(colsMap.get("dataType"));
            row.add(colsMap.get("dataLength"));
            row.add(colsMap.get("priKey"));
            row.add("否");
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

        String sql = "SELECT TABLE_NAME, table_comment COMMENTS FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + DB_DATABASE + "'";
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

        String sql = "select\n" +
                "    table_comment, c.table_name, column_name, data_type, ifnull(character_maximum_length,'') character_maximum_length, pri_key, is_nullable, ifnull(column_default,'') column_default, column_comment\n" +
                "from\n" +
                "    (\n" +
                "        SELECT table_name, table_comment\n" +
                "        FROM INFORMATION_SCHEMA.TABLES\n" +
                "        WHERE table_schema = +'" + DB_DATABASE + "'" +
                "    ) t,\n" +
                "    (\n" +
                "        SELECT\n" +
                "            table_name, column_name, data_type, character_maximum_length,\n" +
                "            case when column_key = 'PRI' then '是' else '否' end pri_key,\n" +
                "            case when is_nullable = 'YES' then '是' else '否' end is_nullable,\n" +
                "            column_default,\n" +
                "            column_comment\n" +
                "        FROM INFORMATION_SCHEMA.COLUMNS\n" +
                "        WHERE table_schema = +'" + DB_DATABASE + "'" +
                "    ) c\n" +
                "where t.table_name = c.table_name";
        try {
            PreparedStatement selectps = conn.prepareStatement(sql);
            ResultSet rs = selectps.executeQuery();
            while(rs.next()){
                Map<String, String> map = new HashMap<>();
                map.put("tableName", rs.getString("TABLE_NAME"));
                map.put("columnName", rs.getString("COLUMN_NAME"));
                map.put("dataType", rs.getString("DATA_TYPE"));
                map.put("dataLength", rs.getString("CHARACTER_MAXIMUM_LENGTH"));
                map.put("nullable", rs.getString("IS_NULLABLE"));
                map.put("priKey", rs.getString("PRI_KEY"));
                map.put("dataDefault", rs.getString("COLUMN_DEFAULT"));
                map.put("comments", rs.getString("COLUMN_COMMENT"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
