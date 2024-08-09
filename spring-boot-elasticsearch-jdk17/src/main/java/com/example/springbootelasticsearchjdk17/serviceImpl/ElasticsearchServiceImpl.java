package com.example.springbootelasticsearchjdk17.serviceImpl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.springbootelasticsearchjdk17.core.util.EsUtil;
import com.example.springbootelasticsearchjdk17.service.ElasticsearchService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Resource
    private EsUtil esUtil;

    private final String indexName = "ddd";

    @Override
    public String createIndex() {
        try {
//            if (esUtil.existsIndex(indexName)) {
//                return "索引已存在";
//            }

            String settingJson = "";
            String mappingJson = "{\"properties\": {\"id\": {\"type\": \"keyword\"},\"name\": {\"type\": \"text\"},\"gender\": {\"type\": \"text\"},\"age\": {\"type\": \"integer\"},\"birthday\": {\"type\": \"date\",\"format\": \"yyyy-MM-dd\"}}}";

            esUtil.createIndex(indexName, settingJson, mappingJson);

        } catch (IOException e) {
            e.printStackTrace();
            return "fail!";
        }
        return "end!";
    }

    @Override
    public String getMapping() {
        try {
            return JSONUtil.toJsonStr(esUtil.getMapping(indexName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String putMapping() {
        String mappingJson = "{\"properties\": {\"phone\": {\"type\": \"text\"}}}";

        try {
            if (esUtil.putMapping(indexName, mappingJson)) {
                return "修改成功";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "修改失败";
    }

    @Override
    public String delIndex() {
        try {
            if (esUtil.delIndex(indexName)) {
                return "success!";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail!";
    }

    @Override
    public String getIndexInfo() {
        try {
            return JSONUtil.toJsonStr(esUtil.getIndexInfo(indexName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String bulkInsert() {

        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", "1");
        row1.put("name", "夏弥");
        row1.put("gender", "女");
        row1.put("age", 19);
        data.add(row1);

        try {
            esUtil.bulkInsert(indexName, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "添加成功";
    }

    @Override
    public String search() {
        try {
//            List<String> list = esUtil.listSuggestCompletion(indexName, "title", "夏", 10);
//            return JSONUtil.toJsonStr(list);

            List<String> list = esUtil.search(indexName);
            return JSONUtil.toJsonStr(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail!";
    }

}
