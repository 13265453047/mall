package com.bruce.springbootdemo.config;

import com.bruce.springbootdemo.init.BeanLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rcy
 * @version 1.0.0
 * @className: BeanConfig
 * @date 2021/9/13 11:54
 */
@Configuration
public class BeanConfig {

    @Bean(initMethod = "initMethod",destroyMethod = "destroy")
    BeanLifecycle beanLifecycle(){
        return new BeanLifecycle();
    }

}
