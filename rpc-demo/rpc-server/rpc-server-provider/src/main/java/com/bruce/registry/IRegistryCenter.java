package com.bruce.registry;
/**
 *@description 服务注册
 *@data 2020/10/6 19:56
 *@author Bruce.Ren
 *@version 1.0
**/
public interface IRegistryCenter {

    /**
     * 服务注册名称和服务注册地址实现服务的管理
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName,String serviceAddress);

}
