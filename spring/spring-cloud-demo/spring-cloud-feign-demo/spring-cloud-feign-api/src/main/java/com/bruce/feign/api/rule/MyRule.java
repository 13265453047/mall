package com.bruce.feign.api.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 负载均衡规则
 *
 * @author rcy
 * @version 1.1.0
 * @className: MyRule
 * @date 2022-09-01
 */
public class MyRule extends AbstractLoadBalancerRule {

    @Override
    public Server choose(Object key) {
        List<Server> serverList = getLoadBalancer().getAllServers();
        return serverList.get(0);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
