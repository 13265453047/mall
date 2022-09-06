package com.bruce.hystrix.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;

/**
 * @author rcy
 * @version 1.1.0
 * @className: OrderApi
 * @date 2022-09-06
 */
@FeignClient(name = "spring-cloud-hystrix-service")
public interface OrderApi1 {

    @GetMapping("api/order1/{orderNo}")
    String order1(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") String orderNo);

}
