package com.bruce.springcloudnacosdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringCloudNacosDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringCloudNacosDemoApplication.class, args);
        String dbHost = context.getEnvironment().getProperty("yp.db.host");
        String driverClass = context.getEnvironment().getProperty("spring.datasource.driver-class-name");

        System.out.println("dbHost = " + dbHost);
        System.out.println("driverClass = " + driverClass);
    }

}
