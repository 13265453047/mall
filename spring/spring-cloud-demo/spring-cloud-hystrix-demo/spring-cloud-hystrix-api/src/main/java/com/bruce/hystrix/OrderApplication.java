package com.bruce.hystrix;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 */
@ComponentScan
@Configuration
@EnableFeignClients
public class OrderApplication {
}
