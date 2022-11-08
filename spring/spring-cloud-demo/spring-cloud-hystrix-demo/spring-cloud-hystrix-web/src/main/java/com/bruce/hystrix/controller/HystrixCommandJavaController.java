package com.bruce.hystrix.controller;

import com.bruce.hystrix.service.HystrixCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;

/**
 * 自定义 HystrixCommand 类
 *
 * @author rcy
 * @version 1.1.0
 * @className: HystrixCommandJavaController
 * @date 2022-09-14
 */
@RestController
@RequestMapping("hystrix")
public class HystrixCommandJavaController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("command/java/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") int orderNo) {
        HystrixCommandService commandService = new HystrixCommandService(orderNo, restTemplate);
        return commandService.execute();
    }

}