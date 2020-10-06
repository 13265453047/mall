package com.bruce.config;

import com.bruce.domain.RpcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *@description TODO
 *@data 2020/10/6 19:30
 *@author Bruce.Ren
 *@version 1.0
**/
@Configuration
@ComponentScan(basePackages="com.bruce")
public class SpringConfig {

    @Bean
    public RpcServer rpcServer(){
        return new RpcServer(8080);
    }


}
