package com.bruce.servie;

import com.bruce.annotation.RpcService;
import com.bruce.entity.User;
import com.bruce.service.IHelloService;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description 服务提供者
 * @data 2020/10/6 19:25
 **/
@RpcService(value = IHelloService.class, version = "v1.0")
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(double money) {
        System.out.println("【V1.0】request in sayHello:" + money);
        return "【V1.0】Say Hello:" + money;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("【V1.0】request in saveUser:" + user);
        return "【V1.0】SUCCESS";
    }
}
