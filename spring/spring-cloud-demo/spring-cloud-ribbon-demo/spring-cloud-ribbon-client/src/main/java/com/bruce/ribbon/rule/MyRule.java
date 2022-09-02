package com.bruce.ribbon.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
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
public class MyRule implements IRule {

    @Override
    public Server choose(Object key) {
        List<Server> serverList = getLoadBalancer().getAllServers();
        return serverList.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {

    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return null;
    }

}
