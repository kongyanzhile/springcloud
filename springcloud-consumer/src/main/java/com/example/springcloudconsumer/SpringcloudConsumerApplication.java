package com.example.springcloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.example.commonfegin.povider"})
@ComponentScan(basePackages = {
        "com.example.springcloudconsumer",
        "com.example.commonfegin.povider",
        "com.example.commonutil"
})
public class SpringcloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumerApplication.class, args);
    }

}
