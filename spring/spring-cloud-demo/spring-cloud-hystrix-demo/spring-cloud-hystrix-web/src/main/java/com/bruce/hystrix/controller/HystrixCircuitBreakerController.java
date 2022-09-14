package com.bruce.hystrix.controller;

import com.bruce.hystrix.service.OrderApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * HystrixCircuitBreaker熔断触发请求降级
 *
 * @author rcy
 * @version 1.1.0
 * @className: HystrixCircuitBreakerController
 * @date 2022-09-06
 */
@RestController
@RequestMapping("hystrix")
public class HystrixCircuitBreakerController {

    @Autowired
    private OrderApi orderApi;

    // HystrixCommandProperties
    @HystrixCommand(
            // 配置熔断的策略
            // 服务熔断后就不会进入该方法，会直接调用 fallbackMethod 中配置的方法
            // 熔断开启之后，后续的正常请求也无法发送过去
            // 触发熔断的阈值：10s钟之内，发起了20次请求，失败率超过50%，就会触发熔断
            // 熔断的恢复时间：熔断时长（时间窗口的配置5s），从熔断开启到后续5s之内的请求，都不会发起远程服务端调用
            // 熔断会有一个自动恢复的机制：5s之后就会尝试去发起远程服务端请求，来检测服务是否正常
            // 熔断是触发降级的一个手段
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 开启熔断
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"), // 最小请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), // 时间窗口，熔断的时长
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), // 错误率
            },
            fallbackMethod = "getOrderFallback"
    )
    @GetMapping("circuitbreaker/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") int orderNo) {
        if (orderNo % 2 == 0) {
            return "success order";
        }
        return orderApi.order(orderNo + "");
    }

    public String getOrderFallback(int orderNo) {
        return "进入 getOrderFallback -> " + orderNo;
    }


}
