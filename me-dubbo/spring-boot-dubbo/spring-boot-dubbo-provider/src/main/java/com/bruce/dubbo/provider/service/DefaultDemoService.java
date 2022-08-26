package com.bruce.dubbo.provider.service;

import com.bruce.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author rcy
 * @version 1.1.0
 * @className: DefaultDemoService
 * @date 2022-08-26
 */
@DubboService(version = "1.0.0", interfaceClass = DemoService.class)
public class DefaultDemoService implements DemoService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

}
