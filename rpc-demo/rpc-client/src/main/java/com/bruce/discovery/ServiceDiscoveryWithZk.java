package com.bruce.discovery;

import com.bruce.config.ZkConfig;
import com.bruce.discovery.loadbalance.LoadBalanceStrategy;
import com.bruce.discovery.loadbalance.impl.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description zk服务发现
 * @data 2020/10/6 20:42
 **/
public class ServiceDiscoveryWithZk implements IServiceDiscovery {
    private Map<String, List<String>> serviceRepos = new HashMap<>(); // 服务地址的本地缓存

    CuratorFramework curatorFramework = null;

    {
        //初始化zookeeper的连接， 会话超时时间是5s，衰减重试
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("registry")
                .build();
        curatorFramework.start();
    }

    @Override
    public String discovery(String serviceName) throws Exception {
        //完成了服务地址的查找(服务地址被删除)
        String path = "/" + serviceName; // registry/com.bruce.service.IHelloService
        List<String> services = serviceRepos.get(path);

        System.out.println("---->" + path);

        if (services == null || services.isEmpty()) {
            registryWatch(path);
        }
        while (services == null || services.isEmpty()) {
            try {
                services = curatorFramework.getChildren().forPath(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.SECONDS.sleep(1);
        }

        // 针对已有的地址做负载均衡
        LoadBalanceStrategy loadBalanceStrategy = new RandomLoadBalance();
        return loadBalanceStrategy.selectService(services);
    }

    /**
     * 监听节点
     *
     * @param path
     */
    private void registryWatch(String path) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework, path, true);

        PathChildrenCacheListener nodeCacheListener = (curatorFramework, event) -> {
            System.out.println("客户端收到节点变更的事件");
            List<String> services = curatorFramework.getChildren().forPath(path);// 再次更新本地的缓存地址
            if (!services.isEmpty() && services.size() > 0) {
                serviceRepos.put(path, services);
            }
        };

        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }
}
