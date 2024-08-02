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

    @GetMapping(value = "/createIndex")
    public String createIndex() {
        return elasticsearchService.createIndex();
    }

}
