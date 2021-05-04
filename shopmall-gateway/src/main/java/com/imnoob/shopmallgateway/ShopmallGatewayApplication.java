package com.imnoob.shopmallgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ShopmallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmallGatewayApplication.class, args);
    }

}
