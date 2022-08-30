package com.bruce.ribbon;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author rcy
 * @version 1.1.0
 * @className: MyConfiguration
 * @date 2022-08-29
 */
@Configuration
public class RibbonConfig {

    /*@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

    // org.springframework:spring-webflux:5.1.5.RELEASE
    /*@Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }*/

}
