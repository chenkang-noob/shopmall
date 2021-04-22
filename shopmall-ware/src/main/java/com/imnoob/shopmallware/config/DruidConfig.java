package com.imnoob.shopmallware.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
public class DruidConfig {

    @Bean
    DataSource druidDatasource(){
        return new DruidDataSource();
    }
}
