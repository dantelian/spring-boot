package com.example.springbootelasticsearch.serviceImpl;

import com.example.springbootelasticsearch.service.ElasticsearchService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void getById(String id) {
        GetRequest getRequest = new GetRequest("customer", id);

        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            String index = getResponse.getIndex();
            String documentId = getResponse.getId();
            System.out.println("index: " + index + " id: " + documentId);
            if (getResponse.isExists()) {
                long version = getResponse.getVersion();
                String sourceAsString = getResponse.getSourceAsString();
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                byte[] sourceAsBytes = getResponse.getSourceAsBytes();
                System.out.println("version: " + version);
                System.out.println("sourceAsMap: " + sourceAsMap);
                System.out.println("sourceAsBytes: " + Arrays.toString(sourceAsBytes));
                System.out.println("sourceAsString: " + sourceAsString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
