package com.bruce.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootRabbitmqProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmqProviderApplication.class, args);
    }

    @GetMapping("/say")
    public String say(){
        return "hello";
    }

}
