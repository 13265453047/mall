package com.bruce.config;

import com.bruce.domain.RpcProxyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description TODO
 * @data 2020/10/6 20:40
 **/
@Configuration
public class SpringConfig {

    @Bean
    public RpcProxyClient rpcProxyClient() {
        return new RpcProxyClient();
    }
}
