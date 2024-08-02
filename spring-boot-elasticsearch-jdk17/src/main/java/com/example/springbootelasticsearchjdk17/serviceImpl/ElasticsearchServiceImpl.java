package com.example.springbootelasticsearchjdk17.serviceImpl;

import com.example.springbootelasticsearchjdk17.core.util.EsUtil;
import com.example.springbootelasticsearchjdk17.service.ElasticsearchService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

}
