package com.bruce.service;

import com.bruce.entity.User;

/**
 *@description TODO
 *@data 2020/10/6 19:21
 *@author Bruce.Ren
 *@version 1.0
**/
public interface IHelloService {

    //
    String sayHello(double money);

    /**
     * 保存用户
     * @param user
     * @return
     */
    String saveUser(User user);

}
