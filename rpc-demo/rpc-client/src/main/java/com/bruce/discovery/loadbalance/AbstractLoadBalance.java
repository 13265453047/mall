package com.bruce.discovery.loadbalance;

import java.util.List;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description TODO
 * @data 2020/10/6 20:58
 **/
public abstract class AbstractLoadBalance implements LoadBalanceStrategy {
    @Override
    public String selectService(List<String> repos) {
        //repos可能为空， 可能只有一个。
        if (repos == null || repos.size() == 0) {
            return null;
        }
        if (repos.size() == 1) {
            return repos.get(0);
        }
        return doSelect(repos);
    }

    protected abstract String doSelect(List<String> repos);

}
