package com.bruce.discovery.loadbalance.impl;

import com.bruce.discovery.loadbalance.AbstractLoadBalance;

import java.util.List;
import java.util.Random;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description TODO
 * @data 2020/10/6 20:59
 **/
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> repos) {
        int length = repos.size();
        Random random = new Random(); // 从repos的集合内容随机获得一个地址
        return repos.get(random.nextInt(length));
    }
}
