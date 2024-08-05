package com.example.springbootelasticsearchjdk17.controller;

import com.example.springbootelasticsearchjdk17.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/es")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    // 创建索引
    @GetMapping(value = "/createIndex")
    public String createIndex() {
        return elasticsearchService.createIndex();
    }

    //获取mapping
    @GetMapping(value = "/getMapping")
    public String getMapping() {
        return elasticsearchService.getMapping();
    }

    // 修改mapping
    @GetMapping(value = "/putMapping")
    public String putMapping() {
        return elasticsearchService.putMapping();
    }

    // 获取索引信息
    @GetMapping(value = "/getIndexInfo")
    public String getIndexInfo() {
        return elasticsearchService.getIndexInfo();
    }

    // 批量插入数据到es
    @GetMapping(value = "/bulkInsert")
    public String bulkInsert() {
        return elasticsearchService.bulkInsert();
    }

    // 查询
    @GetMapping(value = "/search")
    public String search() {
        return elasticsearchService.search();
    }


}
