package com.bruce.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShardingsphereJdbcSpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereJdbcSpringbootMybatisApplication.class, args);
    }

}
