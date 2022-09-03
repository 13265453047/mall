package com.bruce.feign.api.config;

import com.bruce.feign.api.rule.MyRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rcy
 * @data 2022-09-03 15:33
 * @description TODO
 */
@Configuration
public class MyFeignConfiguration {

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule() {
        return new MyRule();
    }

    /**
     * 配置调用服务时feign打印日志的内容
     *
     * @return
     */
    @Bean
    Logger.Level feignLog() {
        return Logger.Level.FULL;
    }

}
