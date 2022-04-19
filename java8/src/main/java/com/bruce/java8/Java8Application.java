package com.bruce.java8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Java8Application {

    public static void main(String[] args) {
        SpringApplication.run(Java8Application.class, args);
    }

}
