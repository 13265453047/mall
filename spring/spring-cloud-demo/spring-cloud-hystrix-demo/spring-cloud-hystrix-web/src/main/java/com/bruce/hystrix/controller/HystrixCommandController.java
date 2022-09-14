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
 * @author rcy
 * @version 1.1.0
 * @className: GpHystrixCommandController
 * @date 2022-09-14
 */
@RestController
@RequestMapping("hystrix")
public class HystrixCommandController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("command/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") int orderNo) {
        HystrixCommandService commandService = new HystrixCommandService(orderNo, restTemplate);
        return commandService.execute();
    }

}
