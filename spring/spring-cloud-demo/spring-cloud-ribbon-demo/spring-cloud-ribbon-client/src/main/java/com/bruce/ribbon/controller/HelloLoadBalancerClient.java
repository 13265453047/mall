//package com.bruce.ribbon.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: HelloClient
// * @date 2022-08-29
// */
//@RestController
//public class HelloLoadBalancerClient {
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private LoadBalancerClient balancerClient;
//
//    @RequestMapping("/hello/{name}")
//    public String hello(@PathVariable(name = "name") String name) {
//        ServiceInstance choose = balancerClient.choose("spring-cloud-ribbon-user");
//        String url = String.format("http://%s:%s", choose.getHost(), choose.getPort() + "/hello/");
//        return restTemplate.getForObject(url + name, String.class);
//    }
//}
//
