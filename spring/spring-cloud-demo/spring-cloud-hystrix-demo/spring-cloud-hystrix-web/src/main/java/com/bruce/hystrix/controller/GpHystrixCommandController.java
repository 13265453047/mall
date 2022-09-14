package com.bruce.hystrix.controller;

import com.bruce.hystrix.annotation.GpHystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;

/**
 * @author rcy
 * @version 1.1.0
 * @className: GpHystrixCommandController
 * @date 2022-09-14
 */
@RestController
@RequestMapping("hystrix")
public class GpHystrixCommandController {

    @Autowired
    private RestTemplate restTemplate;

    @GpHystrixCommand(
            timeout = 2000,
            fallbackMethod = "getOrderFallback"
    )
    @GetMapping("defined/timeout/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") int orderNo) {
        return restTemplate.getForObject("http://localhost:7071/api/order/" + orderNo, String.class);
    }

    public String getOrderFallback(int orderNo) {
        return "请求超时 -> " + orderNo;
    }


}
