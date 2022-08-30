package com.bruce.ribbon;

import com.bruce.ribbon.context.ApplicationContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcy
 * @version 1.1.0
 * @className: UserController
 * @date 2022-08-29
 */
@RestController
public class UserController {

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable(name = "name") String name) {
        Environment environment = ApplicationContextHolder.getApplicationContext().getEnvironment();
        String port = environment.getProperty("server.port");
        System.out.println("======== " + port + " ========");
        return "Hello:" + name;
    }

}
