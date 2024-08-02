package com.example.springbootelasticsearchjdk17.core.config;

import com.example.springbootelasticsearchjdk17.core.properties.RestClientPoolProperties;
import com.example.springbootelasticsearchjdk17.core.util.EsUtil;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


@EnableConfigurationProperties(RestClientPoolProperties.class)
@Configuration
public class RestAutoConfigure {
    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder builder, RestClientPoolProperties poolProperties
            , ElasticsearchProperties restProperties) {
        setRequestConfig(builder, poolProperties);
        setHttpClientConfig(builder, poolProperties, restProperties);
        return new RestHighLevelClient(builder);
    }

    /**
     * 异步httpclient连接延时配置
     */
    private void setRequestConfig(RestClientBuilder builder, RestClientPoolProperties poolProperties) {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(poolProperties.getConnectTimeOut())
                    .setSocketTimeout(poolProperties.getSocketTimeOut())
                    .setConnectionRequestTimeout(poolProperties.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });
    }

    /**
     * 异步httpclient连接数配置
     */
    private void setHttpClientConfig(RestClientBuilder builder, RestClientPoolProperties poolProperties, ElasticsearchProperties restProperties) {
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(poolProperties.getMaxConnectNum())
                    .setMaxConnPerRoute(poolProperties.getMaxConnectPerRoute());

            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(restProperties::getUsername).to(username -> {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY,
                        new UsernamePasswordCredentials(username, restProperties.getPassword()));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
            return httpClientBuilder;
        });
    }

    @Bean
    @ConditionalOnMissingBean
    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }

    @Bean
    public EsUtil esUtil(RestHighLevelClient restHighLevelClient) {
        return new EsUtil(restHighLevelClient);
    }

}
