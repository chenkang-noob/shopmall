package com.imnoob.shopmallorder.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DuridConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    DataSource druidDataSource(){
        return new DruidDataSource();
    }
}
