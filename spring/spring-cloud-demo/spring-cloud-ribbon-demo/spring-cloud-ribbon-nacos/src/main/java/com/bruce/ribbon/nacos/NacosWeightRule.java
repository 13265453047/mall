package com.bruce.ribbon.nacos;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

/**
 * @author rcy
 * @version 1.1.0
 * @className: NacosWeightRule
 * @date 2022-08-29
 */

/**
 * @ClassName NacosWeightRule
 * @Description nacos权重规则实现
 */
public class NacosWeightRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        return null;
    }

//    @Autowired
//    private NacosDiscoveryProperties nacosDiscoveryProperties;
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//
//    }
//
//    @Override
//    public Server choose(Object o) {
//
//        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
//
//        // 获取将要请求的服务的名称
//        String name = loadBalancer.getName();
//
//        // 拿到服务发现的相关api
//        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
//
//        try {
//            // nacos client自动通过基于权重的负载均衡算法，选择一个实例
//            Instance instance = namingService.selectOneHealthyInstance(name);
//
//            return new NacosServer(instance);
//        } catch (NacosException e) {
//            return null;
//        }
//    }
}
