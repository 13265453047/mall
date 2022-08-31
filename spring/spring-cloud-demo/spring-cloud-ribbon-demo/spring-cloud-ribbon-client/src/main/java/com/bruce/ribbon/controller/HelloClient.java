//package com.bruce.ribbon.controller;
//
//import com.bruce.ribbon.RibbonConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
//import org.springframework.cloud.netflix.ribbon.RibbonClients;
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
////@RibbonClients(
////        {@RibbonClient(name = "user-server", configuration = RibbonConfiguration.class)}
////)
//@RibbonClient(name = "user-server", configuration = RibbonConfiguration.class)
//public class HelloClient {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @RequestMapping("/hello/{name}")
//    public String hello(@PathVariable(name = "name") String name) {
//        return restTemplate.getForObject("http://user-server/hello/" + name, String.class);
//    }
//}
//