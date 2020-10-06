package com.bruce.discovery;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description 服务发现
 * @data 2020/10/6 20:39
 **/
public interface IServiceDiscovery {

    /**
     * 根据服务名称返回服务地址
     *
     * @param serviceName
     * @return
     */
    String discovery(String serviceName) throws Exception;
}
