package com.example.springbootelasticsearchjdk17.core.util;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import java.io.IOException;
import java.util.*;


@Slf4j
public class EsUtil {
    private RestHighLevelClient client;

    public EsUtil(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 创建索引及setting及mapping
     *
     * @param indexName
     * @param settingJson
     * @param mappingJson
     * @throws IOException
     */
    public void createIndex(String indexName, String settingJson, String mappingJson) throws IOException {
        //如果存在就不创建了
        if (this.existsIndex(indexName)) {
            log.info("{}索引库已经存在!", indexName);
            throw new RuntimeException(indexName + "索引库已经存在");
        }
        // 开始创建库
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (StrUtil.isNotBlank(settingJson)) {
            request.settings(settingJson, XContentType.JSON);
        } else {
            String setting = JSONUtil.formatJsonStr("{\n" +
                    "    \"max_result_window\": \"1000000\",\n" +
                    "    \"number_of_replicas\": 1,\n" +
                    "    \"number_of_shards\": 1\n" +
                    "  }\n");
            request.settings(setting, XContentType.JSON);
        }
        if (StrUtil.isNotBlank(mappingJson)) {
            request.mapping(mappingJson, XContentType.JSON);
        }

        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean flag = createIndexResponse.isAcknowledged();
        if (flag) {
            log.info("创建索引库:{}成功！", indexName);
        }
    }

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public Boolean existsIndex(String indexName) throws IOException {
        GetIndexRequest getRequest = new GetIndexRequest(indexName);
        getRequest.local(false);
        getRequest.humanReadable(true);
        return client.indices().exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public Boolean delIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        return deleteIndexResponse.isAcknowledged();
    }

    /**
     * 别名是否存在
     *
     * @param indexName
     * @param aliasName
     * @return
     * @throws IOException
     */
    public Boolean isAliasExists(String indexName, String aliasName) throws IOException {
        GetAliasesRequest getAliasesRequest = new GetAliasesRequest(aliasName);
        getAliasesRequest.indices(indexName);
        return client.indices().existsAlias(getAliasesRequest, RequestOptions.DEFAULT);
    }

    /**
     * 创建索引别名
     *
     * @param indexName
     * @param aliasName
     * @return
     * @throws IOException
     */
    public Boolean addAlias(String indexName, String aliasName) throws IOException {
        IndicesAliasesRequest aliasesRequest = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasAction =
                new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index(indexName)
                        .alias(aliasName);
        aliasesRequest.addAliasAction(aliasAction);
        AcknowledgedResponse acknowledgedResponse = client.indices().updateAliases(aliasesRequest, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    /**
     * 删除别名
     *
     * @param indexName
     * @param aliasName
     * @return
     * @throws IOException
     */
    public Boolean deleteAlias(String indexName, String aliasName) throws IOException {
        DeleteAliasRequest deleteAliasRequest = new DeleteAliasRequest(indexName, aliasName);
        org.elasticsearch.client.core.AcknowledgedResponse acknowledgedResponse = client.indices().deleteAlias(deleteAliasRequest, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    /**
     * 根据别名修改索引
     *
     * @param alias
     * @param oldIndexname
     * @param newIndexname
     * @return
     * @throws IOException
     */
    public Boolean changeAliasAfterReindex(String alias, String oldIndexname, String newIndexname) throws IOException {
        IndicesAliasesRequest.AliasActions addIndexAction = new IndicesAliasesRequest.AliasActions(
                IndicesAliasesRequest.AliasActions.Type.ADD).index(newIndexname).alias(alias);
        IndicesAliasesRequest.AliasActions removeAction = new IndicesAliasesRequest.AliasActions(
                IndicesAliasesRequest.AliasActions.Type.REMOVE).index(oldIndexname).alias(alias);

        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
        indicesAliasesRequest.addAliasAction(addIndexAction);
        indicesAliasesRequest.addAliasAction(removeAction);
        AcknowledgedResponse indicesAliasesResponse = client.indices().updateAliases(indicesAliasesRequest,
                RequestOptions.DEFAULT);
        return indicesAliasesResponse.isAcknowledged();
    }

    /**
     * 获取mapping
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public JSONObject getMapping(String indexName) throws IOException {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        GetMappingsResponse mapping = client.indices().getMapping(request, RequestOptions.DEFAULT);
        MappingMetadata mappingMetaData = mapping.mappings().get(indexName);
        Map<String, Object> sourceAsMap = mappingMetaData.getSourceAsMap();
        return new JSONObject(sourceAsMap);
    }

    /**
     * 修改mapping
     *
     * @param indexName
     * @param mapping
     * @return
     * @throws IOException
     */
    public Boolean putMapping(String indexName, String mapping) throws IOException {
        PutMappingRequest request = new PutMappingRequest(indexName);
        request.source(mapping,
                XContentType.JSON);
        AcknowledgedResponse acknowledgedResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    /**
     * 获取索引名称
     *
     * @return
     * @throws IOException
     */
    public List<String> getIndex() throws IOException {
        GetAliasesRequest request = new GetAliasesRequest();
        GetAliasesResponse alias = client.indices().getAlias(request, RequestOptions.DEFAULT);
        Map<String, Set<AliasMetadata>> aliases = alias.getAliases();
        List<String> list = new ArrayList<>();
        aliases.forEach((k, v) -> {
            if (!k.startsWith(StrPool.DOT)) {//忽略elasticesearch 默认的
                list.add(k);
            }
        });
        return list;
    }

    /**
     * 获取索引信息
     *
     * @return
     * @throws IOException
     */
    public JSONArray getIndexInfo(String indexName) throws IOException {
        String endpoint = "/_cat/indices?format=json";
        if (StrUtil.isNotBlank(indexName)) {
            endpoint += "&index=" + indexName;
        }
        RestClient lowLevelClient = client.getLowLevelClient();
        Request request = new Request(RestRequest.Method.GET.name(), endpoint);
        Response response = lowLevelClient.performRequest(request);
        String s = EntityUtils.toString(response.getEntity());
        JSONArray jsonArray = JSONUtil.parseArray(s);
        return jsonArray;
    }

    /**
     * 设置es空间字段
     **/
    public JSONObject getGeometry() {
        JSONObject geometry = new JSONObject();
        geometry.putOpt("type", "geo_shape");
        geometry.putOpt("ignore_malformed", true);
        return geometry;
    }

    /**
     * 获取es矢量瓦片
     **/
    public String getMvt(String index, String field, Map<String, Object> params, Integer x, Integer y, Integer z) {
        String method = RestRequest.Method.GET.name();
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(index).append("/_mvt/").append(field).append("/").append(x).append("/").append(y).append("/").append(z);
        Request request = new Request(method, sb.toString());
        request.addParameter("format", "json");
        JSONObject json = new JSONObject();
        json.putOpt("exact_bounds", (params.containsKey("exact_bounds") && ObjectUtil.isNotNull(params.get("exact_bounds"))) ? params.get("exact_bounds") : true);
        json.putOpt("extent", 4096);
        json.putOpt("grid_precision", (params.containsKey("grid_precision") && ObjectUtil.isNotNull(params.get("grid_precision"))) ? params.get("exact_bounds") : 0);
        json.putOpt("grid_type", (params.containsKey("grid_type") && ObjectUtil.isNotNull(params.get("grid_type"))) ? params.get("exact_bounds") : "grid");
        json.putOpt("track_total_hits", (params.containsKey("track_total_hits") && ObjectUtil.isNotNull(params.get("track_total_hits"))) ? params.get("track_total_hits") : false);
        json.putOpt("size", (params.containsKey("size") && ObjectUtil.isNotNull(params.get("size"))) ? params.get("size") : 10000);
        json.putOpt("fields", (params.containsKey("fields") && ObjectUtil.isNotNull(params.get("fields"))) ? params.get("fields") : Arrays.asList(""));
        request.setJsonEntity(JSONUtil.toJsonStr(json));
        try {
            Response response = this.client.getLowLevelClient().performRequest(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入数据到es
     *
     * @param index 索引名
     * @param data  数据
     **/
    public void bulkInsert(String index, List<Map<String, Object>> data) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        //准备批量插入的数据
        data.forEach(d -> {
            //设置请求对象
            IndexRequest request = new IndexRequest(index);
            request.source(JSONUtil.toJsonStr(d), XContentType.JSON);
            //将request添加到批量处理请求中
            bulkRequest.add(request);
        });
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.debug("批量插入是否失败：" + response.hasFailures());
        for (BulkItemResponse itemResponse : response) {
            BulkItemResponse.Failure failure = itemResponse.getFailure();
            if (failure == null) {
                log.debug("插入成功的文档id：{}", itemResponse.getId());
            } else {
                log.debug("插入失败的文档id：{}", itemResponse.getId());
            }
        }
    }

    public List<String> search(String indexName) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置查询条件，例如匹配所有文档
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
//        hits.forEach(p -> System.out.println("文档原生信息：" + p.getSourceAsString()));

        List<String> hitList = new ArrayList<>();
        hits.forEach(p -> hitList.add(p.getSourceAsString()));
        return hitList;
    }

    /**
     * completion suggest
     *
     * @param suggestField
     * @param suggestValue
     * @param suggestMaxCount
     * @param indexName
     * @return
     */
    public List<String> listSuggestCompletion(String indexName, String suggestField, String suggestValue, Integer suggestMaxCount) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CompletionSuggestionBuilder suggestionBuilderDistrict =
                new CompletionSuggestionBuilder(suggestField + ".suggest").prefix(suggestValue).size(suggestMaxCount);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(suggestField + ".suggest", suggestionBuilderDistrict);
        searchSourceBuilder.suggest(suggestBuilder);
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = null;
        response = client.search(searchRequest, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();
        List<String> keywords = null;
        if (suggest != null) {
            keywords = new ArrayList<>();
            List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries =
                    suggest.getSuggestion(suggestField + ".suggest").getEntries();
            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry : entries) {
                for (Suggest.Suggestion.Entry.Option option : entry.getOptions()) {
                    String keyword = option.getText().string();
                    if (StrUtil.isNotBlank(keyword)) {
                        if (keywords.contains(keyword)) {
                            continue;
                        }
                        keywords.add(keyword);
                        if (keywords.size() >= suggestMaxCount) {
                            break;
                        }
                    }
                }
            }
        }
        return keywords;
    }

}
