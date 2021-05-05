package com.imnoob.shopmallorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "com.imnoob.shopmallorder.mapper")
@SpringBootApplication
public class ShopmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmallOrderApplication.class, args);
    }

}
