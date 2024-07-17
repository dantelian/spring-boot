package com.example.springbootelasticsearch.config.elasticsearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//@Configuration
public class ElasticSearchConfig {

//    /**
//     * 解决netty引起的issue
//     */
//    @PostConstruct
//    void init() {
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//    }
//
//
//    @Bean
//    public RestHighLevelClient getRestClient() {
//
//        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient
//                .builder(new HttpHost("10.35.11.23", 9200, "http")));
//        return restHighLevelClient;
//    }

}
