package com.bruce.feign.api;

import com.bruce.feign.api.config.MyFeignConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author rcy
 * @version 1.1.0
 * @className: FeignApplication
 * @date 2022-08-29
 */
@Configuration
@ComponentScan
@EnableFeignClients(defaultConfiguration = MyFeignConfiguration.class)
public class FeignApplication {
}
