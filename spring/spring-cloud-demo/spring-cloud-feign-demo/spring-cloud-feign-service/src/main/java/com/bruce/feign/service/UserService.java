package com.bruce.feign.service;

import com.bruce.feign.api.service.UserApi;
import com.bruce.feign.context.ApplicationContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcy
 * @version 1.0.0
 * @className: UserApi
 * @date 2022-08-29
 */
@RestController
public class UserService implements UserApi {

    @Override
    public String sayHello(String name) {

        Environment environment = ApplicationContextHolder.getApplicationContext().getEnvironment();
        String port = environment.getProperty("server.port");
        System.out.println("======== " + port + " ========");
        return "Hello:" + name;
    }

}
