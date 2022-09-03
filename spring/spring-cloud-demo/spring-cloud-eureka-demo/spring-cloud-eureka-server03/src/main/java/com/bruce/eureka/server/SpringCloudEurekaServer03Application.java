package com.bruce.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEurekaServer03Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServer03Application.class, args);
    }

}
