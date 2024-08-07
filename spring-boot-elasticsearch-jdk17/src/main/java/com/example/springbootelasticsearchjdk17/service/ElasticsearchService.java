package com.example.springbootelasticsearchjdk17.service;

public interface ElasticsearchService {

    String createIndex();

    String getMapping();

    String putMapping();

    String delIndex();

    String getIndexInfo();

    String bulkInsert();

    String search();

}
