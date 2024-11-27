package com.example.springbootcommon.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PoiUtil {
    private static final String MAIN_SHEET = "信息";
    private static final String SHEET_MAP = "map";
    private static final String ATTRIBUTE_STATUS = "attribute";


    /**
     * 计算formula
     *
     * @param offset   偏移量，如果给0，表示从A列开始，1，就是从B列
     * @param rowId    第几行
     * @param colCount 一共多少列
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
     */
    private static String getRange(int offset, int rowId, int colCount) {
        char start = (char) ('A' + offset);
        if (colCount <= 25) {
            char end = (char) (start + colCount - 1);
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
        } else {
            char endPrefix = 'A';
            char endSuffix = 'A';
            if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
                if ((colCount - 25) % 26 == 0) {// 边界值
                    endSuffix = (char) ('A' + 25);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                }
            } else {// 51以上
                if ((colCount - 25) % 26 == 0) {
                    endSuffix = (char) ('A' + 25);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26 - 1);
                } else {
                    endSuffix = (char) ('A' + (colCount - 25) % 26 - 1);
                    endPrefix = (char) (endPrefix + (colCount - 25) / 26);
                }
            }
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
        }
    }

    //-----------------------------------------------------------

    /**
     * 导出三级联动and单个下拉框的excel
     *
     * @param fileName   文件名
     * @param headers    表头
     * @param mapOneList 一级所有内容
     * @param map        三级联动对应内容
     * @param list       导出数据
     */
    public static void export(HttpServletResponse response, String fileName, List<String> headers, List<String> mapOneList, Map<String, List<String>> map, List list) {

        // 属性单个下拉数据
        List<String> attributeList = new ArrayList<String>();
        attributeList.add("是");
        attributeList.add("否");

        // 2.创建Excel
        // 1)创建workbook
        HSSFWorkbook hssfWorkBook = new HSSFWorkbook();
        // 2)创建sheet
        HSSFSheet mainSheet = hssfWorkBook.createSheet(MAIN_SHEET);// 主sheet
        // 用于展示
        //2.1 创建表头，供用户输入
        initHeaders(hssfWorkBook, mainSheet, headers);
        //导出数据到主sheet
        setMainSheet(mainSheet, list);

        // attribute 下拉框 sheet
        HSSFSheet attributeSheet = hssfWorkBook.createSheet(ATTRIBUTE_STATUS);

        //三级联动 sheet
        HSSFSheet mapSheet = hssfWorkBook.createSheet(SHEET_MAP);

        // true:隐藏/false:显示
        //省市区关系sheet
        hssfWorkBook.setSheetHidden(hssfWorkBook.getSheetIndex(mapSheet), false);// 设置sheet是否隐藏
        //单个下拉框sheet
        hssfWorkBook.setSheetHidden(hssfWorkBook.getSheetIndex(attributeSheet), false);// 设置sheet是否隐藏

        // 3.写入数据
        writeData(hssfWorkBook, mapSheet, mapOneList, map);// 将数据写入隐藏的sheet中并做好关联关系
        //3.1 写入单个下拉数据
        writeDropDownData(hssfWorkBook, attributeSheet, attributeList, ATTRIBUTE_STATUS);
        // 4.设置数据有效性
        setDataValid(hssfWorkBook, mainSheet, mapOneList, map);
//        FileOutputStream os = null;
//        try {
//            String exisname = filePath.substring(0, filePath.lastIndexOf("/"));
//            File f = new File(exisname);
//            if (!f.exists()) {
//                f.mkdirs();//创建目录
//            }
//            // 创建可写入的Excel工作簿
//            File file = new File(filePath);
//            if (!file.exists()) {
//                boolean bool = file.createNewFile();
//                System.out.println(bool);
//            } else {
//                file.delete();
//                file.createNewFile();
//            }
//            os = new FileOutputStream(filePath);
//            hssfWorkBook.write(os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(os);
//        }
        try {
            write(response, hssfWorkBook, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(HttpServletResponse response, HSSFWorkbook book, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String name = new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        ServletOutputStream out = response.getOutputStream();
        book.write(out);
        out.flush();
        out.close();
    }

    /**
     * 生成主页面表头
     *
     * @param wb
     * @param mainSheet
     * @param headers
     */
    public static void initHeaders(HSSFWorkbook wb, HSSFSheet mainSheet, List<String> headers) {
        //表头样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        //字体样式
        HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("微软雅黑");
        fontStyle.setFontHeightInPoints((short) 12);
        fontStyle.setBold(true);
        style.setFont(fontStyle);
        //生成主内容
        HSSFRow rowFirst = mainSheet.createRow(0);//第一个sheet的第一行为标题
        mainSheet.createFreezePane(0, 1, 0, 1); //冻结第一行
        //写标题
        for (int i = 0; i < headers.size(); i++) {
            HSSFCell cell = rowFirst.createCell(i); //获取第一行的每个单元格
            mainSheet.setColumnWidth(i, 4000); //设置每列的列宽
            cell.setCellStyle(style); //加样式
            cell.setCellValue(headers.get(i)); //往单元格里写数据
        }
    }


    private static void setMainSheet(HSSFSheet mainSheet, List list) {
        for (int j = 0; j < list.size(); j++) {
            //行
            Row row = mainSheet.createRow(j + 1);
            Object obj = list.get(j);
            if (obj != null) {
                Field[] fields = obj.getClass().getDeclaredFields();
                try {
                    for (int i = 0; i < fields.length; i++) {
                        if (i == 0) {
                            //excel 第一列 为序号
                            Cell cell = row.createCell(i);
                            cell.setCellValue(j + 1);
                        } else {
                            Field f = fields[i];
                            f.setAccessible(true);
                            String name = f.getName();
                            Object value = f.get(obj);
                            if (value == null) {
                                value = "";
                            }
                            //列
                            Cell cell = row.createCell(i);
                            cell.setCellValue(value.toString());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private static void setDataValid(HSSFWorkbook HSSFWorkBook, HSSFSheet mainSheet, List<String> provinceList, Map<String, List<String>> siteMap) {
        //设置省份下拉
        HSSFDataValidationHelper dvHelper = new HSSFDataValidationHelper((HSSFSheet) mainSheet);
        String[] dataArray = provinceList.toArray(new String[0]);
        HSSFSheet hidden = HSSFWorkBook.createSheet("hidden");
        HSSFCell cell = null;
        for (int i = 0, length = dataArray.length; i < length; i++) {
            String name = dataArray[i];
            HSSFRow row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }

        Name namedCell = HSSFWorkBook.createName();
        namedCell.setNameName("hidden");
        namedCell.setRefersToFormula("hidden!$A$1:$A$" + dataArray.length);
        //加载数据,将名称为hidden的
        DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
        // 四个参数分别是：起始行、终止行、起始列、终止列.
        // 1 (省份下拉框代表从excel第1+1行开始) 10(省份下拉框代表从excel第1+10行结束) 1(代表第几列开始，0是第一列，1是第二列) 1(代表第几列结束，0是第一列，1是第二列)
        CellRangeAddressList provinceRangeAddressList = new CellRangeAddressList(1, 10, 1, 1);
        DataValidation provinceDataValidation = dvHelper.createValidation(constraint, provinceRangeAddressList);
        provinceDataValidation.createErrorBox("error", "请选择正确");
        provinceDataValidation.setShowErrorBox(true);
        // provinceDataValidation.setSuppressDropDownArrow(true);
        //将第二个sheet设置为隐藏
        // true:隐藏/false:显示
        HSSFWorkBook.setSheetHidden(HSSFWorkBook.getSheetIndex(hidden), true);// 设置sheet是否隐藏
//        HSSFWorkBook.setSheetHidden(1, true);
        mainSheet.addValidationData(provinceDataValidation);


        //设置属性下拉
        DataValidationConstraint attributeConstraint = dvHelper.createFormulaListConstraint(ATTRIBUTE_STATUS);
        // 四个参数分别是：起始行、终止行、起始列、终止列
        // 1 (下拉框代表从excel第1+1行开始) 10(下拉框代表从excel第1+10行结束) 5(代表第几列开始，0是第一列，1是第二列) 5(代表第几列结束，0是第一列，1是第二列)
        CellRangeAddressList attributeRangeAddressList = new CellRangeAddressList(1, 10, 5, 5);
        HSSFDataValidation attributeDataValidation = (HSSFDataValidation) dvHelper.createValidation(attributeConstraint, attributeRangeAddressList);
        attributeDataValidation.createErrorBox("Error", "请选择或输入有效的选项，或下载最新重试！");
        // genderDataValidation.setSuppressDropDownArrow(true);
        mainSheet.addValidationData(attributeDataValidation);

        // 设置市、区下拉
        // i <= 10 ,10代表市、区下拉框到10+1行结束
        for (int i = 0; i <= 10; i++) {
            setDataValidation('B', mainSheet, i + 1, 2);// "B"是指父类所在的列，i+1初始值为1代表从第2行开始，2要与“B”对应，为B的列号加1，假如第一个参数为“C”，那么最后一个参数就3
        }

    }

    /**
     * 设置有效性
     *
     * @param offset 主影响单元格所在列，即此单元格由哪个单元格影响联动
     * @param sheet
     * @param rowNum 行数
     * @param colNum 列数
     */
    private static void setDataValidation(char offset, HSSFSheet sheet, int rowNum, int colNum) {
        HSSFDataValidationHelper dvHelper = new HSSFDataValidationHelper(sheet);
        DataValidation dataValidationList1;
        DataValidation dataValidationList2;
        dataValidationList1 = getDataValidationByFormula("INDIRECT($" + offset + (rowNum) + ")", rowNum, colNum, dvHelper);
        dataValidationList2 = getDataValidationByFormula("INDIRECT($" + (char) (offset + 1) + (rowNum) + ")", rowNum, colNum + 1, dvHelper);
        sheet.addValidationData(dataValidationList1);
        sheet.addValidationData(dataValidationList2);
    }


    private static DataValidation getDataValidationByFormula(String formulaString, int naturalRowIndex, int naturalColumnIndex, HSSFDataValidationHelper dvHelper) {
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(formulaString);
        CellRangeAddressList regions = new CellRangeAddressList(naturalRowIndex, 65535, naturalColumnIndex, naturalColumnIndex);
        HSSFDataValidation data_validation_list = (HSSFDataValidation) dvHelper.createValidation(dvConstraint, regions);
        data_validation_list.setEmptyCellAllowed(false);
        if (data_validation_list instanceof HSSFDataValidation) {
            // data_validation_list.setSuppressDropDownArrow(true);
            data_validation_list.setShowErrorBox(true);
        } else {
            // data_validation_list.setSuppressDropDownArrow(false);
        }
        // 设置输入信息提示信息
        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        return data_validation_list;
    }

    /**
     * 循环单个下拉框的数据写入genderSheet的第A列中
     *
     * @param hssfWorkBook
     * @param sheet
     * @param list
     * @param name         sheet名称
     */
    private static void writeDropDownData(HSSFWorkbook hssfWorkBook, HSSFSheet sheet, List<String> list, String name) {
        //循环单个下拉框的数据写入genderSheet的第A列中
        for (int i = 0; i < list.size(); i++) {
            HSSFRow genderRow = sheet.createRow(i);
            genderRow.createCell(0).setCellValue(list.get(i));
        }
        initGenderMapping(hssfWorkBook, sheet.getSheetName(), list.size(), name);// 创建数据规则
    }

    private static void writeData(HSSFWorkbook hssfWorkBook, HSSFSheet mapSheet, List<String> provinceList, Map<String, List<String>> siteMap) {

        //循环将父数据写入siteSheet的第1行中
        int siteRowId = 0;
        HSSFRow provinceRow = mapSheet.createRow(siteRowId++);
        provinceRow.createCell(0).setCellValue("父列表");
        for (int i = 0; i < provinceList.size(); i++) {
            provinceRow.createCell(i + 1).setCellValue(provinceList.get(i));
        }
        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
        Iterator<String> keyIterator = siteMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            List<String> son = siteMap.get(key);
            HSSFRow siteRow = mapSheet.createRow(siteRowId++);
            siteRow.createCell(0).setCellValue(key);
            for (int i = 0; i < son.size(); i++) {
                siteRow.createCell(i + 1).setCellValue(son.get(i));
            }

            // 添加名称管理器
            String range = getRange(1, siteRowId, son.size());
            Name name = hssfWorkBook.createName();
            name.setNameName(key);
            String formula = mapSheet.getSheetName() + "!" + range;
            name.setRefersToFormula(formula);
        }
    }


    /**
     * 创建下拉数据规则
     *
     * @param workbook
     * @param genderSheetName
     * @param genderQuantity
     * @param name
     */
    private static void initGenderMapping(HSSFWorkbook workbook, String genderSheetName, int genderQuantity, String name) {
        Name genderName = workbook.createName();
        genderName.setNameName(name);
        genderName.setRefersToFormula(genderSheetName + "!$A$1:$A$" + genderQuantity);
    }


    /**
     * 给sheet页，添加下拉列表
     *
     * @param workbook    excel文件，用于添加Name
     * @param targetSheet 级联列表所在sheet页
     * @param options     级联数据 ['百度','阿里巴巴']
     * @param column      下拉列表所在列 从'A'开始
     * @param fromRow     下拉限制开始行
     * @param endRow      下拉限制结束行
     */
    public static void addValidationToSheet(Workbook workbook, Sheet targetSheet, Object[] options, char column, int fromRow, int endRow) {
        String hiddenSheetName = "sheet" + workbook.getNumberOfSheets();
        Sheet optionsSheet = workbook.createSheet(hiddenSheetName);
        String nameName = column + "_parent";

        int rowIndex = 0;
        for (Object option : options) {
            int columnIndex = 0;
            Row row = optionsSheet.createRow(rowIndex++);
            Cell cell = row.createCell(columnIndex++);
            cell.setCellValue(option.toString());
        }

        createName(workbook, nameName, hiddenSheetName + "!$A$1:$A$" + options.length);

        DVConstraint constraint = DVConstraint.createFormulaListConstraint(nameName);
        CellRangeAddressList regions = new CellRangeAddressList(fromRow, endRow, (int) column - 'A', (int) column - 'A');
        targetSheet.addValidationData(new HSSFDataValidation(regions, constraint));
    }

    /**
     * 给sheet页  添加级联下拉列表
     *
     * @param workbook    excel
     * @param targetSheet sheet页
     * @param options     要添加的下拉列表内容  ， keys 是下拉列表1中的内容，每个Map.Entry.Value 是对应的级联下拉列表内容
     * @param keyColumn   下拉列表1位置
     * @param valueColumn 级联下拉列表位置
     * @param fromRow     级联限制开始行
     * @param endRow      级联限制结束行
     */
    public static void addValidationToSheet(Workbook workbook, Sheet targetSheet, Map<String, List<String>> options, char keyColumn, char valueColumn, int fromRow, int endRow) {
        String hiddenSheetName = "sheet" + workbook.getNumberOfSheets();
        Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);
        List<String> firstLevelItems = new ArrayList<>();

        int rowIndex = 0;
        for (Map.Entry<String, List<String>> entry : options.entrySet()) {
            String parent = formatNameName(entry.getKey());
            firstLevelItems.add(parent);
            List<String> children = entry.getValue();

            int columnIndex = 0;
            Row row = hiddenSheet.createRow(rowIndex++);
            Cell cell = null;

            for (String child : children) {
                cell = row.createCell(columnIndex++);
                cell.setCellValue(child);
            }

            char lastChildrenColumn = (char) ((int) 'A' + children.size() - 1);
            createName(workbook, parent, String.format(hiddenSheetName + "!$A$%s:$%s$%s", rowIndex, lastChildrenColumn, rowIndex));

            DVConstraint constraint = DVConstraint.createFormulaListConstraint("INDIRECT($" + keyColumn + "1)");
            CellRangeAddressList regions = new CellRangeAddressList(fromRow, endRow, valueColumn - 'A', valueColumn - 'A');
            targetSheet.addValidationData(new HSSFDataValidation(regions, constraint));
        }

        addValidationToSheet(workbook, targetSheet, firstLevelItems.toArray(), keyColumn, fromRow, endRow);

    }

    /**
     * 根据用户在keyColumn选择的key, 自动填充value到valueColumn
     *
     * @param workbook    excel
     * @param targetSheet sheet页
     * @param keyValues   匹配关系 {'百度','www.baidu.com'},{'淘宝','www.taobao.com'}
     * @param keyColumn   要匹配的列（例如 网站中文名称）
     * @param valueColumn 匹配到的内容列（例如 网址）
     * @param fromRow     下拉限制开始行
     * @param endRow      下拉限制结束行
     */
    public static void addAutoMatchValidationToSheet(Workbook workbook, Sheet targetSheet, Map<String, String> keyValues, char keyColumn, char valueColumn, int fromRow, int endRow) {
        String hiddenSheetName = "sheet" + workbook.getNumberOfSheets();
        Sheet hiddenSheet = workbook.createSheet(hiddenSheetName);

        // init the search region(A and B columns in hiddenSheet)
        int rowIndex = 0;
        for (Map.Entry<String, String> kv : keyValues.entrySet()) {
            Row totalSheetRow = hiddenSheet.createRow(rowIndex++);

            Cell cell = totalSheetRow.createCell(0);
            cell.setCellValue(kv.getKey());

            cell = totalSheetRow.createCell(1);
            cell.setCellValue(kv.getValue());
        }

        for (int i = fromRow; i <= endRow; i++) {
            Row totalSheetRow = targetSheet.getRow(i);
            if (totalSheetRow == null) {
                totalSheetRow = targetSheet.createRow(i);
            }

            Cell cell = totalSheetRow.getCell((int) valueColumn - 'A');
            if (cell == null) {
                cell = totalSheetRow.createCell((int) valueColumn - 'A');
            }

            String keyCell = String.valueOf(keyColumn) + (i + 1);
            String formula = String.format("IF(ISNA(VLOOKUP(%s,%s!A:B,2,0)),\"\",VLOOKUP(%s,%s!A:B,2,0))", keyCell, hiddenSheetName, keyCell, hiddenSheetName);

            cell.setCellFormula(formula);
        }

        // init the keyColumn as comboList
        addValidationToSheet(workbook, targetSheet, keyValues.keySet().toArray(), keyColumn, fromRow, endRow);
    }

    private static Name createName(Workbook workbook, String nameName, String formula) {
        Name name = workbook.createName();
        name.setNameName(nameName);
        name.setRefersToFormula(formula);
        return name;
    }

    /**
     * 隐藏excel中的sheet页
     *
     * @param workbook
     * @param start    需要隐藏的 sheet开始索引
     */
    private static void hideTempDataSheet(HSSFWorkbook workbook, int start) {
        for (int i = start; i < workbook.getNumberOfSheets(); i++) {
            workbook.setSheetHidden(i, true);
        }
    }

    /**
     * 不可数字开头
     *
     * @param name
     * @return
     */
    static String formatNameName(String name) {
        name = name.replaceAll(" ", "").replaceAll("-", "_").replaceAll(":", ".");
        if (Character.isDigit(name.charAt(0))) {
            name = "_" + name;
        }

        return name;
    }


}
