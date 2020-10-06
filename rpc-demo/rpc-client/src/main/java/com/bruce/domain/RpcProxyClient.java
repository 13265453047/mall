package com.bruce.domain;

import com.bruce.discovery.IServiceDiscovery;
import com.bruce.discovery.ServiceDiscoveryWithZk;

import java.lang.reflect.Proxy;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description 客户端请求代理
 * @data 2020/10/6 20:41
 **/
public class RpcProxyClient {

    private IServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithZk();

    public <T> T clientProxy(final Class<?> interfaceClass, String version) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new RemoteInvocationHandler(serviceDiscovery,version)
        );
    }

}
