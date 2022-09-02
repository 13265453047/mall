//package com.bruce.ribbon;
//
//import com.bruce.ribbon.rule.MyRule;
//import com.netflix.loadbalancer.AvailabilityFilteringRule;
//import com.netflix.loadbalancer.IPing;
//import com.netflix.loadbalancer.IRule;
//import com.netflix.loadbalancer.PingUrl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: RibbonConfiguration
// * @date 2022-08-29
// */
//@Configuration
//public class RibbonConfiguration {
//
//    @Bean
//    public IPing ribbonPing() {
//        return new PingUrl();
//    }
//
//    @Bean
//    public IRule ribbonRule() {
////        //随机
////        new RandomRule();
////        //轮询
////        new RoundRobinRule();
////        //权重
////        new WeightedResponseTimeRule();
////        //最小并发
////        new BestAvailableRule();
////        //区域感知——就近原则
////        new ZoneAvoidanceRule();
////        return new AvailabilityFilteringRule();
//        return new MyRule();
//    }
//}
