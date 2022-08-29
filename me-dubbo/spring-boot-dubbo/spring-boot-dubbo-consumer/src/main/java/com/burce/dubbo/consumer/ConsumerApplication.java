package com.burce.dubbo.consumer;

import com.bruce.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
@EnableAutoConfiguration
public class ConsumerApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference(version = "1.0.0", interfaceClass = DemoService.class)
//    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:20880")
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> logger.info(demoService.sayHello("mercyblitz"));
    }

}
