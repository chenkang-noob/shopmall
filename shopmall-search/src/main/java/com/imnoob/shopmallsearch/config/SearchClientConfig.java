package com.imnoob.shopmallsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchClientConfig {

    @Bean
    RestHighLevelClient restHighLevelClient(){
        RestClientBuilder restClientBuilder = null;
        restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return  restHighLevelClient;
    }
}
