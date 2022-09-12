package com.bruce.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

//@EnableHystrix
// 开启熔断
@EnableCircuitBreaker
@SpringBootApplication
public class SpringCloudHystrixWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixWebApplication.class, args);
    }

}
