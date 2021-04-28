package com.imnoob.shopmallproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.imnoob.shopmallproduct.mapper")
@SpringBootApplication
@EnableFeignClients(basePackages = "com.imnoob.shopmallproduct.feign")
@EnableDiscoveryClient
public class ShopmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmallProductApplication.class, args);
    }

}
