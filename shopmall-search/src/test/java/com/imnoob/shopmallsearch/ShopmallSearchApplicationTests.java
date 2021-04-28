package com.imnoob.shopmallsearch;

import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShopmallSearchApplicationTests {

    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient);
    }

}
