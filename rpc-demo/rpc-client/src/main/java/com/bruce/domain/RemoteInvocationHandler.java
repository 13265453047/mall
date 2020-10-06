package com.bruce.domain;

import com.bruce.discovery.IServiceDiscovery;
import com.bruce.transport.RpcNetTransport;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description TODO
 * @data 2020/10/6 20:45
 **/
public class RemoteInvocationHandler implements InvocationHandler {

    private String version;
    private IServiceDiscovery serviceDiscovery;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);
        String serviceName = rpcRequest.getClassName();
        if (!StringUtils.isEmpty(version)) {
            serviceName = serviceName + "-" + version;
        }
        String serviceAddress = serviceDiscovery.discovery(serviceName);
        //远程通信
        RpcNetTransport netTransport = new RpcNetTransport(serviceAddress);
        return netTransport.send(rpcRequest);
    }
}
