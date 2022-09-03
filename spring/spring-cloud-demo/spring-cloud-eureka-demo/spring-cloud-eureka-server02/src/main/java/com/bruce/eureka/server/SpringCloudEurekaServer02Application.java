package com.bruce.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEurekaServer02Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServer02Application.class, args);
    }

}
