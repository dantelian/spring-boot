package com.example.springbootcommon.common.easyExcel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author admin
 * @date 2020/5/17
 * @desc
 */
public class DownHandler implements SheetWriteHandler {

    private final Map<Integer, String[]> dropDownMap;

    private int index;

    public DownHandler(Map<Integer, String[]> dropDownMap) {
        this.dropDownMap = dropDownMap;
        this.index = 0;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        ///开始设置下拉框 HSSFWorkbook
        DataValidationHelper helper = sheet.getDataValidationHelper();
        Field[] fields = writeWorkbookHolder.getClazz().getDeclaredFields();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            if(fields[i].isAnnotationPresent(DropDown.class)){
                dropDown(helper, sheet, i, fields[i].getDeclaredAnnotation(DropDown.class).value());
            }
        }
        if (dropDownMap == null) {
            return;
        }
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        dropDownMap.forEach((celIndex, value) -> {
            if(value.length>20){
                dropDownBigData(helper, workbook ,sheet, celIndex, value);
            } else {
                dropDown(helper, sheet, celIndex, value);
            }
        });
    }

    private void dropDown(DataValidationHelper helper, Sheet sheet, Integer celIndex, String[] value) {
        if(null== value || value.length<=0){
            return;
        }
        this.dropDown(helper, sheet, celIndex, helper.createExplicitListConstraint(value));
    }

    private void dropDownBigData(DataValidationHelper helper, Workbook workbook, Sheet sheet, Integer celIndex, String[] v) {
        // 定义sheet的名称
        String sheetName = "sheet" + celIndex;
        // 1.创建一个隐藏的sheet 名称为 proviceSheet
        Sheet sheet1 = workbook.createSheet(sheetName);
        // 从第二个工作簿开始隐藏
        this.index++;
        // 设置隐藏
        workbook.setSheetHidden(this.index, true);
        // 2.循环赋值
        for (int i = 0, length = v.length; i < length; i++) {
            // i:表示你开始的行数 0表示你开始的列数
            sheet1.createRow(i).createCell(0).setCellValue(v[i]);
        }
        Name name = workbook.createName();
        name.setNameName(sheetName);
        //代表 以A列1行开始获取N行下拉数据
        name.setRefersToFormula(sheetName + "!$A$1:$A$" + (v.length));
        // 设置下拉
        this.dropDown(helper, sheet, celIndex, helper.createFormulaListConstraint(sheetName));
    }

    private void dropDown(DataValidationHelper helper, Sheet sheet, Integer celIndex, DataValidationConstraint constraint) {
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(1, 100, celIndex, celIndex);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            //数据校验
            dataValidation.setSuppressDropDownArrow(true);
            //错误提示
            dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            dataValidation.createErrorBox("提示", "此值与单元格定义数据不一致");
            dataValidation.setShowErrorBox(true);
            //选定提示
            dataValidation.createPromptBox("填写说明：","填写内容只能为下拉中数据，其他数据将导致导入失败");
            dataValidation.setShowPromptBox(true);
            sheet.addValidationData(dataValidation);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

}
