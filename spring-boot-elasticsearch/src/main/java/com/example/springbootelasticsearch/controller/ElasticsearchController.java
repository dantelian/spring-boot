package com.example.springbootelasticsearch.controller;


import com.example.springbootelasticsearch.service.ElasticsearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/es")
public class ElasticsearchController {

    @Resource
    private ElasticsearchService elasticsearchService;

    /**
     * 根据id查询索引：customer
     * @param id
     */
    @GetMapping("/getById/{id}")
    public void getById(@PathVariable("id") String id) {
        elasticsearchService.getById(id);
    }

}
