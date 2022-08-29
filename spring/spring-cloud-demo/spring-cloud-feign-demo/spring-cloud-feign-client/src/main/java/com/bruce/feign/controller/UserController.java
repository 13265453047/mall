package com.bruce.feign.controller;

import com.bruce.feign.api.service.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcy
 * @version 1.0.0
 * @className: UserApi
 * @date 2022-08-29
 */
@RestController
public class UserController {

    @Autowired
    private UserApi userApi;

    @PostMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return userApi.sayHello(name);
    }

}
