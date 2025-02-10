package com.example.springbootcommon.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.string.StringStringConverter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.springbootcommon.common.easyExcel.*;
import com.example.springbootcommon.common.util.EasyExcelUtil;
import com.example.springbootcommon.model.easyexcel.ImageModel;
import com.example.springbootcommon.model.easyexcel.UserCascadeSelectModel;
import com.example.springbootcommon.model.easyexcel.UserModel;
import com.example.springbootcommon.service.EasyExcelService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class EasyExcelServiceImpl implements EasyExcelService {

    @Override
    public void exportExcelMultistageHeader(HttpServletResponse response) throws IOException {
        List<List<String>> headTitles = ListUtils.newArrayList();
        //表头可以根据实际情况进行修改, 相邻列同名自动合并
        headTitles.add(ListUtils.newArrayList( "编号"));
        headTitles.add(ListUtils.newArrayList( "分类一", "金额"));
        headTitles.add(ListUtils.newArrayList( "分类一", "日期"));
        headTitles.add(ListUtils.newArrayList( "分类一", "数目"));
        headTitles.add(ListUtils.newArrayList( "分类二", "金额"));
        headTitles.add(ListUtils.newArrayList( "分类二", "日期"));
        headTitles.add(ListUtils.newArrayList( "分类二", "数目"));
        headTitles.add(ListUtils.newArrayList("长度"));

        List<List<Object>> contentList = ListUtils.newArrayList();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        //数据根据实际获取进行填充
        contentList.add(ListUtils.newArrayList("0001", "190.76", "2024-05-10", "12", "6.78", "2024-02-15","11","183.97"));
        contentList.add(ListUtils.newArrayList("0001", "190.77", "2024-05-10", "13", "6.88", "2024-02-15","14","183.97"));

        EasyExcelUtil.exportExcelMultistageHeader("fileName", "sheetName", headTitles, contentList, response);
    }

    @Override
    public void exportExcelSelect(HttpServletResponse response) throws IOException {
        List<UserModel> contentData = new ArrayList<UserModel>() {{
            add(new UserModel(1, "夏弥", "女", "13612345678", null));
            add(new UserModel(2, "夏达", "女", "13912345678", "管理员"));
        }};

        Map<Integer, String[]> dropDownMap = new HashMap<>();
//        dropDownMap.put(2, "男,女".split(","));
        dropDownMap.put(4, "普通用户,管理员".split(","));

        EasyExcelUtil.exportExcelSelect("exportExcelSelect", "sheetName", contentData, dropDownMap, response, UserModel.class);
    }

    @Override
    public void exportExcelCascadeSelect(HttpServletResponse response) throws IOException {
        // 准备数据
        // 数据
        List<UserCascadeSelectModel> contentData = new ArrayList<UserCascadeSelectModel>() {{
            add(new UserCascadeSelectModel(1, "夏弥", "女", "13612345678", null));
            add(new UserCascadeSelectModel(2, "夏达", "女", "13912345678", "管理员"));
        }};

        // 一级下拉
        SelectItem selectItem = new SelectItem(5);
        selectItem.addDataItem(ListUtils.newArrayList("浙江省","河南省"));
        // 二级下拉
        SelectItem subSelectItem = new SelectItem(6);
        subSelectItem.addDataItem("浙江省",ListUtils.newArrayList("杭州市","宁波市"));
        subSelectItem.addDataItem("河南省",ListUtils.newArrayList("郑州市","洛阳市","开封市"));
        selectItem.setSubSelect(subSelectItem);
        // 三级下拉
        SelectItem selectItem3 = new SelectItem(7);
        selectItem3.addDataItem("杭州市",ListUtils.newArrayList("滨江区","西湖区"));
        selectItem3.addDataItem("宁波市",ListUtils.newArrayList("宁波市1","宁波市2"));
        selectItem3.addDataItem("郑州市",ListUtils.newArrayList("金水区","二七区"));
        selectItem3.addDataItem("洛阳市",ListUtils.newArrayList("洛阳市1","洛阳市2"));
        selectItem3.addDataItem("开封市",ListUtils.newArrayList("开封市1","开封市2"));
        subSelectItem.setSubSelect(selectItem3);

        List<SelectItem> selectItems = ListUtils.newArrayList(selectItem);

        EasyExcelUtil.exportExcelCascadeSelect("exportExcelCascadeSelect", "sheetName", contentData, selectItems, response, UserCascadeSelectModel.class);
    }

    @Override
    public void exportExcelImage(HttpServletResponse response) throws IOException {
        List<ImageModel> contentData = new ArrayList<ImageModel>() {{
            add(new ImageModel("夏弥", new URL("https://picx.zhimg.com/v2-5ff9fb52f7607b5ec1648ee16049e8e5_1440w.jpg")));
            add(new ImageModel("夏达", new URL("https://pic2.zhimg.com/v2-8d3f288feae0e511dee5c3d6735ca999_1440w.jpg")));
        }};

        String fileName = "exportExcelImage";
        String sheetName = "sheetName";
        EasyExcel.write(EasyExcelUtil.getOutputStream(fileName, response), ImageModel.class)
                .excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
                .registerWriteHandler(EasyExcelUtil.getCellStyle())
                .doWrite(contentData);
    }

    @Override
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        List<ImageModel> contentData = new ArrayList<ImageModel>() {{
            add(new ImageModel("夏弥", new URL("https://picx.zhimg.com/v2-5ff9fb52f7607b5ec1648ee16049e8e5_1440w.jpg")));
            add(new ImageModel("夏达", new URL("https://pic2.zhimg.com/v2-8d3f288feae0e511dee5c3d6735ca999_1440w.jpg")));
        }};

        // 模板文件路径
//        String templatePath = "C:\\Users\\ddd\\Desktop\\easy_excel_temp.xlsx";
        String templatePath = this.getClass().getClassLoader().getResource("templates/easy_excel_temp.xlsx").getPath();;
        String fileName = "exportExcelTemplate";
        String sheetName = "sheetName";

        EasyExcel.write(EasyExcelUtil.getOutputStream(fileName, response))
                .withTemplate(templatePath)
                .sheet(sheetName)
                .registerWriteHandler(new CustomRowHeightStyleStrategy(100))
                .registerWriteHandler(EasyExcelUtil.getCellStyle())
                .doWrite(contentData);
    }

    @Override
    public void exportExcelParamsTemplate(HttpServletResponse response) throws IOException {
        // 表单信息
        Map<String, Object> data = new HashMap<>();
        data.put("name", "夏弥");
        data.put("age", "16");
        data.put("phone", "13912345678");
        data.put("date", DateUtils.format(new Date()));
        data.put("pic", new URL("https://pic2.zhimg.com/v2-8d3f288feae0e511dee5c3d6735ca999_1440w.jpg"));

        // 表格信息
        List<Map<String, Object>> titles = new ArrayList<Map<String, Object>>() {{
            add(new HashMap<String, Object>(3) {{
                put("title1", "t1");
                put("title2", "t2");
                put("title3", "t3");
            }});
            add(new HashMap<String, Object>(3) {{
                put("title1", "t11");
                put("title2", "t22");
                put("title3", "t33");
            }});
        }};

        // 模板文件路径
//        String templatePath = "C:\\Users\\ddd\\Desktop\\easy_excel_params_temp.xlsx";
        String templatePath = this.getClass().getClassLoader().getResource("templates/easy_excel_params_temp.xlsx").getPath();;
        String fileName = "exportExcelParamsTemplate";
        ExcelWriter excelWriter = EasyExcel.write(EasyExcelUtil.getOutputStream(fileName, response))
                .withTemplate(templatePath)
                .build();

        WriteSheet writeSheet = EasyExcel.writerSheet()
                .registerWriteHandler(new ImageModifyHandler()) // 图片展示处理
                .build();
        // 填充普通占位符
        // 填入表单信息 这里 data 使用对象或者 Map 都可以
        excelWriter.fill(data, writeSheet);

        // 填充配置，开启组合填充换行
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();
        // 填入表格信息
        excelWriter.fill(titles, fillConfig, writeSheet);

        //填充完成
        excelWriter.finish();
    }

    @Override
    public List<UserModel> importExcelClass(MultipartFile file) throws IOException {
        List<UserModel> list = EasyExcel.read(file.getInputStream())
//                .registerConverter(new StringStringConverter())
//                .registerConverter(new DateConverter())
                .head(UserModel.class)
                .sheet()
                .headRowNumber(1)
                .doReadSync();
        return list;
    }

}
