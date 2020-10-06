package com.bruce.discovery.loadbalance;

import java.util.List;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description 服务发现负载均衡
 * @data 2020/10/6 20:57
 **/
public interface LoadBalanceStrategy {

    String selectService(List<String> repos);

}
