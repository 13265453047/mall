package com.bruce.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author rcy
 * @version 1.1.0
 * @className: RibbonConfiguration
 * @date 2022-08-29
 */
public class RibbonConfiguration {

    @Autowired
    private IClientConfig clientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig clientConfig) {
        return new PingUrl();
    }

    @Bean
    public IRule ribbonRule(IClientConfig clientConfig) {
//        //随机
//        new RandomRule();
//        //轮询
//        new RoundRobinRule();
//        //权重
//        new WeightedResponseTimeRule();
//        //最小并发
//        new BestAvailableRule();
//        //区域感知——就近原则
//        new ZoneAvoidanceRule();
        return new AvailabilityFilteringRule();
    }
}
