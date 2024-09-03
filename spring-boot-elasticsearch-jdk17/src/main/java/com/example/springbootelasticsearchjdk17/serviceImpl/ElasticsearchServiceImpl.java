package com.example.springbootelasticsearchjdk17.serviceImpl;

import cn.hutool.json.JSONUtil;
import com.example.springbootelasticsearchjdk17.core.util.EsUtil;
import com.example.springbootelasticsearchjdk17.service.ElasticsearchService;
import jakarta.annotation.Resource;
import org.elasticsearch.index.query.*;
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
            String mappingJson = "{\"properties\": {\"id\": {\"type\": \"keyword\"},\"name\": {\"type\": \"text\"},\"gender\": {\"type\": \"text\"},\"age\": {\"type\": \"integer\"},\"birthday\": {\"type\": \"date\",\"format\": \"yyyy-MM-dd\"},\"describe\": {\"type\": \"text\"}}}";

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
        row1.put("describe", "夏弥要小心哦，不要被泡了哦，防火防盗防师兄哦！");
        data.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("id", "2");
        row2.put("name", "夏达");
        row2.put("gender", "女");
        row2.put("age", 20);
        row2.put("describe", "夏达,中国知名漫画家");
        data.add(row2);

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
            // 方式一：查询全部
//            List<String> list = esUtil.search(indexName);

            // 方式二：模糊查询
//            List<String> list = esUtil.matchQuery(indexName, "name", "目");

            // 方式三：条件查询
            // 1、词条查询，不会对查询条件进行分词
//            QueryBuilder query = QueryBuilders.termQuery("name","夏目");
            // 2、模糊查询
//            WildcardQueryBuilder query = QueryBuilders.wildcardQuery("name", "*弥");//华后多个字符
            // 3、正则查询
//            RegexpQueryBuilder query = QueryBuilders.regexpQuery("name", "\\w+(.)*");
            // 4、前缀查询
//            PrefixQueryBuilder query = QueryBuilders.prefixQuery("name", "夏");
            // 5、范围查询 以 age 为条件
//            RangeQueryBuilder query = QueryBuilders.rangeQuery("age");
//            query.gte(10); // 指定下限
//            query.lte(19); // 指定上限
            // 6、多字段查询 name + gender ：夏 and 弥
//            QueryStringQueryBuilder query = QueryBuilders.queryStringQuery("夏弥").field("name").field("gender").defaultOperator(Operator.AND);
            // 7、布尔查询 对多个查询条件连接（must（and）：条件必须成立；must_not（not）：条件必须不成立；should（or）：条件可以成立；filter：条件必须成立，性能比must高）
//            BoolQueryBuilder query = QueryBuilders.boolQuery();
//            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", "弥");
//            query.filter(matchQuery);
//            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
//            rangeQuery.gte(10); // 指定下限
//            rangeQuery.lte(19); // 指定上限
//            query.filter(rangeQuery);

//            List<String> list = esUtil.conditionQuery(indexName, query);

            // 方式四：高亮查询
            BoolQueryBuilder query = QueryBuilders.boolQuery();
            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", "弥");
            query.should(matchQuery);
            MatchQueryBuilder matchQuery1 = QueryBuilders.matchQuery("describe", "弥");
            query.should(matchQuery1);
            List<String> list = esUtil.highlightQuery(indexName, query, "name", "describe");

            return JSONUtil.toJsonStr(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail!";
    }

}
