package com.bruce.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;

/**
 * HystrixTimeout超时触发服务降级
 *
 * @author rcy
 * @version 1.1.0
 * @className: HystrixTimeOutController
 * @date 2022-09-06
 */
@RestController
@RequestMapping("hystrix")
public class HystrixTimeoutController {

    @Autowired
    private RestTemplate restTemplate;

    // HystrixCommandProperties
    @HystrixCommand(
            // 配置请求超时的策略
            // 通过浏览器访问发现请求等待时长为2s中，这里明明配置的是3s中，why？
            // restTemplate 本身也有超时时间，还未触发 Hystrix 超时时长时，restTemplate 已经超时异常了
            commandProperties = {
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"), // 超时开启，默认是开启的
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),// 超时时间
            },
            fallbackMethod = "getOrderFallback"
    )
    @GetMapping("timeout/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") int orderNo) {
        if (orderNo % 2 == 0) {
            return "success order";
        }
        return restTemplate.getForObject("http://localhost:7071/api/order/" + orderNo, String.class);
    }

    public String getOrderFallback(int orderNo) {
        return "请求超时 -> " + orderNo;
    }

}
