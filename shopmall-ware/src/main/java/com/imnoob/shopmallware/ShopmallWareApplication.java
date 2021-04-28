package com.imnoob.shopmallware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.imnoob.shopmallware.mapper")
public class ShopmallWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopmallWareApplication.class, args);
    }
}