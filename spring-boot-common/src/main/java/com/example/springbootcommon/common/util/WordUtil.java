package com.example.springbootcommon.common.util;

import com.microsoft.schemas.vml.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

/**
 * @program: open-ai-center
 * @description:
 * @create: 2021-11-12 15:43
 **/
@Slf4j
public class WordUtil {

     static Logger logger = LoggerFactory.getLogger(WordUtil.class);

    /**
     * 水印字体字体
     */
    private static final String fontName = "PingFang SC";

    /**
     *  一个字平均长度，单位pt，用于：计算文本占用的长度（文本总个数*单字长度）
     */
    private static final int widthPerWord = 10;

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

    /**
     * word文字水印(调用poi封装的createWatermark方法)
     * @param doc XWPFDocument对象
     * @param markStr 水印文字
     */
    public static void setWordWaterMark(XWPFDocument doc, String markStr,String fontColor) {
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
        if (headerFooterPolicy == null) {
            headerFooterPolicy = doc.createHeaderFooterPolicy();
        }
        // create default Watermark - fill color black and not rotated
        headerFooterPolicy.createWatermark(markStr);
        // get the default header
        // Note: createWatermark also sets FIRST and EVEN headers
        // but this code does not updating those other headers
        XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
        paragraph = header.getParagraphArray(0);
//            // get com.microsoft.schemas.vml.CTShape where fill color and rotation is set
        paragraph.getCTP().newCursor();
        org.apache.xmlbeans.XmlObject[] xmlobjects = paragraph.getCTP().getRArray(0).getPictArray(0).selectChildren(
                new javax.xml.namespace.QName("urn:schemas-microsoft-com:vml", "shape"));
        if (xmlobjects.length > 0) {
            CTShape ctshape = (CTShape) xmlobjects[0];
            ctshape.setFillcolor(fontColor);
            ctshape.setStyle(ctshape.getStyle() + ";rotation:315");
        }
    }

    /**
     * 将指定的字符串重复repeats次.
     * @param pattern 字符串
     * @param repeats 重复次数
     * @return 生成的字符串
     */
    private static String repeatString(String pattern, int repeats) {
        StringBuilder buffer = new StringBuilder(pattern.length() * repeats);
        Stream.generate(() -> pattern).limit(repeats).forEach(buffer::append);
        return new String(buffer);
    }

    /**
     * 为文档添加水印
     * 实现参考了{@link XWPFHeaderFooterPolicy#(String, int)}
     * @param doc 需要被处理的docx文档对象
     * @param customText 水印文本
     * @param type 类型：1.平铺；2.单个
     */
    private static void waterMarkDocXDocument(XWPFDocument doc, String customText, String styleTop, int type,String fontColor,String fontSize,String rotation) {
        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT); // 如果之前已经创建过 DEFAULT 的Header，将会复用之
        int size = header.getParagraphs().size();
        if (size == 0) {
            header.createParagraph();
        }

        CTP ctp = header.getParagraphArray(0).getCTP();
        byte[] rsidr = doc.getDocument().getBody().getPArray(0).getRsidR();
        byte[] rsidrdefault = doc.getDocument().getBody().getPArray(0).getRsidRDefault();
        ctp.setRsidP(rsidr);
        ctp.setRsidRDefault(rsidrdefault);
        CTPPr ppr = ctp.addNewPPr();
        ppr.addNewPStyle().setVal("Header");

        // 开始加水印
        CTR ctr = ctp.addNewR();
        CTRPr ctrpr = ctr.addNewRPr();
        ctrpr.addNewNoProof();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        CTTextPath shapeTypeTextPath = shapetype.addNewTextpath();
        shapeTypeTextPath.setOn(STTrueFalse.T);
        shapeTypeTextPath.setFitshape(STTrueFalse.T);
        com.microsoft.schemas.office.office.CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.VIEW);
        CTShape shape = group.addNewShape();
        shape.setId("PowerPlusWaterMarkObject");
        shape.setSpid("_x0000_s102");
        shape.setType("#_x0000_t136");

        // 平铺或单个
        if(type != 2){
            // 设置形状样式（旋转，位置，相对路径等参数）
            shape.setStyle(getShapeStyle(customText, styleTop,rotation));
        }else{
            // 设置形状样式（旋转，位置，相对路径等参数）
            shape.setStyle(getShapeStyle());
        }

        shape.setFillcolor(fontColor);
        // 字体设置为实心
        shape.setStroked(STTrueFalse.FALSE);
        // 绘制文本的路径
        CTTextPath shapeTextPath = shape.addNewTextpath();
        // 设置文本字体与大小
        shapeTextPath.setStyle("font-family:" + fontName + ";font-size:" + fontSize);
        shapeTextPath.setString(customText);
        CTPicture pict = ctr.addNewPict();
        pict.set(group);
    }

    /**
     * 以艺术字方式加上水印(平铺)
     * @param docx XWPFDocument对象
     * @param customText 水印文字
     */
    public static void makeFullWaterMarkByWordArt(XWPFDocument docx, String customText,String fontColor,String fontSize,String styleRotation) {
        // 水印文字之间使用8个空格分隔
        customText = customText + repeatString(" ", 16);
        // 一行水印重复水印文字次数
        customText = repeatString(customText, 10);
        // 与顶部的间距
        String styleTop = "0pt";

        if (docx == null) {
            return;
        }

        // 遍历文档，添加水印
        for (int lineIndex = -10; lineIndex < 20; lineIndex++) {
            styleTop = 200 * lineIndex + "pt";
            waterMarkDocXDocument(docx, customText, styleTop, 1,fontColor, fontSize ,styleRotation);
        }
    }

    /**
     * 以艺术字方式加上水印(单个)
     * @param docx XWPFDocument对象
     * @param customText 水印文字
     */
    public static void makeWaterMarkByWordArt(XWPFDocument docx, String customText,String fontColor,String fontSize,String rotation) {
        // 与顶部的间距
        String styleTop = "0pt";
        // 判断文档是否为空
        if (docx == null) {
            return;
        }

        // 添加水印
        waterMarkDocXDocument(docx, customText, styleTop, 2,fontColor,fontSize,rotation);
    }

    /**
     * 构建Shape的样式参数
     * @param customText 水印文本
     * @return
     */
    private static String getShapeStyle(String customText, String styleTop,String styleRotation) {
        StringBuilder sb = new StringBuilder();
        // 文本path绘制的定位方式
        sb.append("position: ").append("absolute");
        // 计算文本占用的长度（文本总个数*单字长度）
        sb.append(";width: ").append(customText.length() * widthPerWord).append("pt");
        // 字体高度
        sb.append(";height: ").append("20pt");
        sb.append(";z-index: ").append("-251654144");
        sb.append(";mso-wrap-edited: ").append("f");
        sb.append(";margin-top: ").append(styleTop);
        sb.append(";mso-position-horizontal-relative: ").append("margin");
        sb.append(";mso-position-vertical-relative: ").append("margin");
        sb.append(";mso-position-vertical: ").append("left");
        sb.append(";mso-position-horizontal: ").append("center");
        sb.append(";rotation: ").append(styleRotation);
        return sb.toString();
    }

    /**
     * 构建Shape的样式参数
     * @return
     */
    private static String getShapeStyle() {
        StringBuilder sb = new StringBuilder();
        // 文本path绘制的定位方式
        sb.append("position: ").append("absolute");
        sb.append(";left: ").append("opt");
        // 计算文本占用的长度（文本总个数*单字长度）
        sb.append(";width: ").append("500pt");
        // 字体高度
        sb.append(";height: ").append("150pt");
        sb.append(";z-index: ").append("-251654144");
        sb.append(";mso-wrap-edited: ").append("f");
        sb.append(";margin-left: ").append("-50pt");
        sb.append(";margin-top: ").append("270pt");
        sb.append(";mso-position-horizontal-relative: ").append("margin");
        sb.append(";mso-position-vertical-relative: ").append("margin");
        sb.append(";mso-width-relative: ").append("page");
        sb.append(";mso-height-relative: ").append("page");
        sb.append(";rotation: ").append("-2949120f");
        return sb.toString();
    }

}
