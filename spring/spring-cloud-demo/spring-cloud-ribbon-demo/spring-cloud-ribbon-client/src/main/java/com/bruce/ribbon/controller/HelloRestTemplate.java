package com.bruce.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author rcy
 * @version 1.1.0
 * @className: HelloClient
 * @date 2022-08-29
 */
@RestController
public class HelloRestTemplate {

    /*@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @RequestMapping("/hello/{name}")
//    public String hello(@PathVariable(name = "name") String name) {
//        return restTemplate.getForObject("http://localhost:8081/hello/" + name, String.class);
//    }
}

