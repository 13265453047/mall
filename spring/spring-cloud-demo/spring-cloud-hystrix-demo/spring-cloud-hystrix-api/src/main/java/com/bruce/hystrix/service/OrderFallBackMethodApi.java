package com.bruce.hystrix.service;

import com.bruce.hystrix.fallbackmethod.OrderFallBackMethod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;

/**
 * @author rcy
 * @version 1.0.0
 * @className: OrderFallBackMethodApi
 * @date 2022-09-06
 */
@FeignClient(name = "spring-cloud-hystrix-service", fallback = OrderFallBackMethod.class)
public interface OrderFallBackMethodApi {

    @GetMapping("api/order/fall/back/method/{orderNo}")
    String orderFallBackMethod(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") String orderNo);

}
