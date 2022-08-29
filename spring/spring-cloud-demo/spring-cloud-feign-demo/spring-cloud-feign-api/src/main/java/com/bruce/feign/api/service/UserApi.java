package com.bruce.feign.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author rcy
 * @version 1.0.0
 * @className: UserApi
 * @date 2022-08-29
 */
@FeignClient(name = "user-service")
public interface UserApi {

    @PostMapping("/api/say/hello/{name}")
    String sayHello(@PathVariable("name") String name);

}
