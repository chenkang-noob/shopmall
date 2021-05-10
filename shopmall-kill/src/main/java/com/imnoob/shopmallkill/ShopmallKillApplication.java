package com.imnoob.shopmallkill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableScheduling
@EnableRabbit
@EnableDiscoveryClient
@MapperScan(basePackages = "com.imnoob.shopmallkill.mapper")
@SpringBootApplication
public class ShopmallKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmallKillApplication.class, args);
    }

}
