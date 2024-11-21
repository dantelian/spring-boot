package com.example.springbootcommon.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.example.springbootcommon.common.util.ApachePoiExcelUtils;
import com.example.springbootcommon.common.util.PoiUtil;
import com.example.springbootcommon.model.apachePoi.ApachePoiExcelExportVo;
import com.example.springbootcommon.model.apachePoi.ApachePoiExcelImportVo;
import com.example.springbootcommon.service.ApachePoiExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApachePoiExcelServiceImpl implements ApachePoiExcelService {

    @Override
    public void exportMap(HttpServletResponse response) throws MalformedURLException {
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄","性别","头像", "城市", ApachePoiExcelUtils.COLUMN_MERGE); // ExcelUtils.COLUMN_MERGE 合并列
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("诸葛亮");
        user1.add(60);
        user1.add("男");
        user1.add(new URL("https://picx.zhimg.com/v2-5ff9fb52f7607b5ec1648ee16049e8e5_1440w.jpg"));
        user1.add("北京");
        user1.add("合并");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(28);
        user2.add("女");
        user2.add(new URL("https://pic2.zhimg.com/v2-8d3f288feae0e511dee5c3d6735ca999_1440w.jpg"));
        user2.add("上海");
        user2.add(ApachePoiExcelUtils.ROW_MERGE); // 合并行
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
        ApachePoiExcelUtils.export(response,"用户表", sheetDataList, selectMap);
    }

    @Override
    public void exportVo(HttpServletResponse response) {
        List<ApachePoiExcelExportVo> list = new ArrayList<>();
        ApachePoiExcelExportVo test1 = new ApachePoiExcelExportVo();
        test1.setNum("1");
        test1.setName("aa");
        test1.setPhone("aa");
        test1.setSex(1);
        list.add(test1);

        ApachePoiExcelUtils.export(response, "测试", list, ApachePoiExcelExportVo.class);
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
        ApachePoiExcelUtils.exportManySheet(response, "学生成绩表", sheets);
    }

    @Override
    public void exportExcelCascadeSelect(HttpServletResponse response) {
        //文件名称
        String fileName = "exportExcelCascadeSelect";
        //excel 表头
        List<String> headers = Arrays.asList("序号","省","市", "区", "无", "属性");
        //所有一级
        List<String> mapOneList = new ArrayList<String>();
        mapOneList.add("广东省");
        mapOneList.add("湖北省");
        //关系map
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.put("广东省", Arrays.asList("广州市", "佛山市"));
        map.put("湖北省", Arrays.asList("武汉市", "荆州市"));
        map.put("广州市", Arrays.asList("白云区", "越秀区"));
        map.put("佛山市", Arrays.asList("顺德区", "南海区"));
        //需要显示在excel的信息
        List list = new ArrayList();
        PoiUtil.export(response, fileName, headers, mapOneList, map, list);
    }


    @Override
    public JSONArray importToJSON(MultipartFile file) throws Exception {
        JSONArray array = ApachePoiExcelUtils.readMultipartFile(file);
        System.out.println("导入数据为:" + array);
        return array;
    }

    @Override
    public void importToVo(MultipartFile file) throws Exception {
        List<ApachePoiExcelImportVo> list = ApachePoiExcelUtils.readMultipartFile(file, ApachePoiExcelImportVo.class);
        for (ApachePoiExcelImportVo vo : list) {
            System.out.println(vo.toString());
        }
    }

    @Override
    public void importManySheet(MultipartFile file) throws Exception {
        Map<String, JSONArray> map = ApachePoiExcelUtils.readFileManySheet(file);
        map.forEach((key, value) -> {
            System.out.println("Sheet名称：" + key);
            System.out.println("Sheet数据：" + value);
            System.out.println("----------------------");
        });
    }

}
