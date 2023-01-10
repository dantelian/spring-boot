package com.example.springbootcommon.common.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * @program: open-ai-center
 * @description:
 * @create: 2021-11-12 15:43
 **/
public class WordUtil {

    // 设置页面大小
    public static void setPageSize(XWPFDocument doc, Integer w, Integer h) {
        CTBody body = doc.getDocument().getBody();
        if (!body.isSetSectPr()) {
            body.addNewSectPr();
        }
        CTSectPr section = body.getSectPr();
        if (!section.isSetPgSz()) {
            section.addNewPgSz();
        }
        CTPageSz pageSize = section.getPgSz();
        pageSize.setW(BigInteger.valueOf(w));
        pageSize.setH(BigInteger.valueOf(h));
        pageSize.setOrient(STPageOrientation.LANDSCAPE);
    }

    // 设置页边距
    public static void setPageMar(XWPFDocument doc, Integer left, Integer top, Integer right, Integer bottom) {
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(left));
        pageMar.setTop(BigInteger.valueOf(top));
        pageMar.setRight(BigInteger.valueOf(right));
        pageMar.setBottom(BigInteger.valueOf(bottom));
    }

    // 创建文章题目
    public static void createThemeParagraph(XWPFDocument document, String text, Integer size) {
        XWPFParagraph titleParagraph = document.createParagraph();    //新建一个标题段落对象（就是一段文字）
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);//样式居中
        XWPFRun titleFun = titleParagraph.createRun();    //创建文本对象
        titleFun.setText(text); //设置标题的名字
        titleFun.setBold(true); //加粗
        titleFun.setColor("000000");//设置颜色
        titleFun.setFontSize(size == null ? 25 : size);    //字体大小
        titleFun.setFontFamily("宋体");//设置字体
    }

    // 创建标题
    public static void createTitleParagraph(XWPFDocument document, String text, int size, String fontFamily) {
        XWPFParagraph titleParagraph = document.createParagraph(); // 新建一个标题段落对象（就是一段文字）
        titleParagraph.setAlignment(ParagraphAlignment.LEFT); // 样式居中
        XWPFRun titleFun = titleParagraph.createRun(); // 创建文本对象
        titleFun.setText(text); // 设置标题的名字
        titleFun.setBold(true); // 加粗
        titleFun.setColor("000000"); // 设置颜色
        titleFun.setFontSize(size); // 字体大小
        titleFun.setFontFamily(fontFamily); // 设置字体
    }

    /**
     * 创建图片
     * @param document
     * @param is
     * @param pictureType
     * @param filename
     * @param width
     * @param height
     */
    public static void createPicParagraph(XWPFDocument document, InputStream is, int pictureType, String filename, int width, int height) throws IOException, InvalidFormatException {
        XWPFParagraph titleParagraph = document.createParagraph(); // 新建一个标题段落对象（就是一段文字）
        titleParagraph.setAlignment(ParagraphAlignment.CENTER); // 样式居中
        XWPFRun titleFun = titleParagraph.createRun(); // 创建文本对象
        titleFun.addPicture(is, pictureType, filename, Units.toEMU(width), Units.toEMU(height));
    }

    /**
     * @Description: 设置表格总宽度与水平对齐方式
     */
    public static void setTableWidthAndHAlign(XWPFTable table, String width,
                                       STJc.Enum enumValue) {
        CTTblPr tblPr = getTableCTTblPr(table);
        // 表格宽度
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        if (enumValue != null) {
            CTJc cTJc = tblPr.addNewJc();
            cTJc.setVal(enumValue);
        }
        // 设置宽度
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }

    /**
     * @Description: 得到Table的CTTblPr,不存在则新建
     */
    public static CTTblPr getTableCTTblPr(XWPFTable table) {
        CTTbl ttbl = table.getCTTbl();
        // 表格属性
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        return tblPr;
    }

    /**
     *  表格布局类型
     * @param infoTable
     */
    public static void setTblLayoutType(XWPFTable infoTable) {
        CTTblPr tblPr = infoTable.getCTTbl().getTblPr();
        CTTblLayoutType t = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
        t.setType(STTblLayoutType.FIXED);
    }

    /**
     * 设置表格行高
     * @param infoTable
     * @param heigth 高度
     * @param vertical 表格内容的显示方式：居中、靠右...
     */
    public static void setTableHeight(XWPFTable infoTable, int heigth, STVerticalJc.Enum vertical) {
        List<XWPFTableRow> rows = infoTable.getRows();
        for(XWPFTableRow row : rows) {
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            CTHeight ht = trPr.addNewTrHeight();
            ht.setVal(BigInteger.valueOf(heigth));
            List<XWPFTableCell> cells = row.getTableCells();
            for(XWPFTableCell tableCell : cells ) {
                CTTcPr cttcpr = tableCell.getCTTc().addNewTcPr();
                cttcpr.addNewVAlign().setVal(vertical);
            }
        }
    }

    /**
     * 跨列合并
     * @param table
     * @param row    所合并的行
     * @param fromCell    起始列
     * @param toCell    终止列
     * @Description
     */
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for(int cellIndex = fromCell; cellIndex <= toCell; cellIndex++ ) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if(cellIndex == fromCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    /**
     * 跨行合并
     * @param table
     * @param col    合并的列
     * @param fromRow    起始行
     * @param toRow    终止行
     * @Description
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for(int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            //第一个合并单元格用重启合并值设置
            if(rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                //合并第一个单元格的单元被设置为“继续”
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * 往表格中填充数据
     * @param table
     * @param tableData
     */
    public static void fillTableData(XWPFTable table, List<List<Object>> tableData) {
        List<XWPFTableRow> rowList = table.getRows();
        for(int i = 0; i < tableData.size(); i++) {
            List<Object> list = tableData.get(i);
            List<XWPFTableCell> cellList = rowList.get(i).getTableCells();
            for(int j = 0; j < list.size(); j++) {
                XWPFParagraph cellParagraph = cellList.get(j).getParagraphArray(0);
                XWPFRun cellParagraphRun = cellParagraph.getRuns().get(0);
                cellParagraphRun.setText(String.valueOf(list.get(j)));
            }
        }
    }
}
