package com.imnoob.shopmallsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ShopmallSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmallSearchApplication.class, args);
    }

}
