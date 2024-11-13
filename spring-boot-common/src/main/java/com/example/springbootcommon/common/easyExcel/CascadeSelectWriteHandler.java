package com.example.springbootcommon.common.easyExcel;

import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 级联下拉处理器
 */
public class CascadeSelectWriteHandler implements SheetWriteHandler, CellWriteHandler {

    private static final int ROW_SIZE = 10000;

    private final WriteFont writeFont;

    private final List<SelectItem> selectItems;

    private final String HIDDEN_SHEET_NAME = "hidden_sheet";

    private final Set<Integer> selectColumns = new HashSet<>();

    private boolean isLoadSelectColumns = false;

    private int rowIndex = 0;

    public CascadeSelectWriteHandler(List<SelectItem> selectItems) {
        Assert.notEmpty(selectItems, "selectItems can not be empty");
        this.selectItems = selectItems;
        // 红色字体
//        writeFont = getRedFont();
        // 默认字体
        writeFont = new WriteFont();
        // 设置字体大小
        writeFont.setFontHeightInPoints((short) 14);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet hiddenSheet = workbook.getSheet(HIDDEN_SHEET_NAME);
        if (hiddenSheet != null) {
            return ;
        }
        hiddenSheet = workbook.createSheet(HIDDEN_SHEET_NAME);
        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet),true);
        Sheet sheet = writeSheetHolder.getSheet();
        for (SelectItem selectItem : selectItems) {
            buildHiddenSheetSelectRef(workbook, sheet, hiddenSheet, selectItem,null);
        }
        if (!isLoadSelectColumns) {
            isLoadSelectColumns = true;
        }
    }

    private void buildHiddenSheetSelectRef(Workbook workbook, Sheet sheet, Sheet hiddenSheet, SelectItem selectItem, String formulaRef) {
        if (!isLoadSelectColumns) {
            selectColumns.add(selectItem.getColumnIndex());
        }
        List<SelectItem.DataItem> dataItems = selectItem.getDataItems();
        for (SelectItem.DataItem dataItem : dataItems) {
            setDataAndName(workbook, hiddenSheet, dataItem);
        }
        // 单元格地址引用
        if (formulaRef == null || formulaRef.isEmpty()){
            formulaRef = dataItems.get(0).getHiddenFormulaRef();
        }
        // 创建检验器
        DataValidation dataValidation = getDataValidation(sheet, selectItem, formulaRef);
        sheet.addValidationData(dataValidation);
        SelectItem subSelect = selectItem.getSubSelect();
        if (subSelect != null){
            buildHiddenSheetSelectRef(workbook, sheet, hiddenSheet, subSelect, getInDirectFormulaRef(selectItem.getColumnIndex()));
        }
    }

    private DataValidation getDataValidation(Sheet sheet, SelectItem selectItem, String formulaRef) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createFormulaListConstraint(formulaRef);
        CellRangeAddressList rangeAddressList = new CellRangeAddressList(1, ROW_SIZE, selectItem.getColumnIndex(), selectItem.getColumnIndex());
        DataValidation dataValidation = helper.createValidation(constraint, rangeAddressList);
        dataValidation.setShowErrorBox(true);
        return dataValidation;
    }

    private void setDataAndName(Workbook workbook, Sheet hiddenSheet, SelectItem.DataItem dataItem) {
        // 构建隐藏数据
        Row row = hiddenSheet.createRow(rowIndex);
        List<String> values = dataItem.getValues();
        for (int i = 0; i < values.size(); i++) {
            row.createCell(i).setCellValue(values.get(i));
        }
        // 创建名称命名器
        Name name = workbook.createName();
        name.setNameName(dataItem.getMappingKey());
        name.setRefersToFormula(getFormulaRef(row));
        dataItem.setHiddenFormulaRef(name.getRefersToFormula());
        rowIndex++;
    }

    private String getInDirectFormulaRef(Integer columnIndex){
        CellReference slectCellReference = new CellReference(1, columnIndex);
        return "INDIRECT(" + joinFormulaRef(slectCellReference, false) + ")";
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (!context.getHead()){
            Integer columnIndex = context.getColumnIndex();
            if (selectColumns.contains(columnIndex)){
                // 设置字体
                context.getFirstCellData().getOrCreateStyle().setWriteFont(writeFont);
            }
        }
        CellWriteHandler.super.afterCellDispose(context);
    }

    private String getFormulaRef(Row prvRow) {
        Cell startCell = prvRow.getCell(prvRow.getFirstCellNum());
        Cell endCell = prvRow.getCell(prvRow.getLastCellNum() - 1);
        return HIDDEN_SHEET_NAME + "!" + joinFormulaRef(new CellReference(startCell),true) + ":" + joinFormulaRef(new CellReference(endCell),true);
    }

    public String joinFormulaRef(CellReference cellReference, boolean isAbsolute){
        StringBuilder sb = new StringBuilder();
        String[] refs = cellReference.getCellRefParts();
        for (int i = refs.length -1 ; i >= 1; i--) {
            if (isAbsolute) {
                sb.append("$");
            }
            sb.append(refs[i]);
        }
        return sb.toString();
    }

    /**
     * 返回一个红色字体
     * @return
     */
    private WriteFont getRedFont() {
        WriteFont redFont = new WriteFont();
        redFont.setColor(IndexedColors.RED.getIndex());
        return redFont;
    }

}
