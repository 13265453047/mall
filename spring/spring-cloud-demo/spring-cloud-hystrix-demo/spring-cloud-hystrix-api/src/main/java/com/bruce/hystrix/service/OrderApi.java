package com.bruce.hystrix.service;

import com.bruce.hystrix.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;

/**
 * @author rcy
 * @version 1.1.0
 * @className: OrderApi
 * @date 2022-09-06
 */
// 警告: 在同一个微服务里面，无论有多少个Feign文件，@FeignClient注解里面的name、path两个属性的必须保持一致，contextId 不能重复，否则其他微服务调用的时候必然会出现问题
// @FeignClient(name = "spring-cloud-hystrix-service")
// @FeignClient(name = "spring-cloud-hystrix-service", contextId = "order-server", path = "/api", fallback = OrderApi.OrderApiHystrix.class)
@FeignClient(name = "spring-cloud-hystrix-service", fallback = OrderApi.OrderApiHystrix.class)
public interface OrderApi {

    @GetMapping("/order/{orderNo}")
    String order(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") String orderNo);

    @PostMapping("order/add")
    int addOrder(@RequestBody OrderDto dto);

    @Component
    class OrderApiHystrix implements OrderApi {

        @Override
        public String order(@NotBlank(message = "订单编号不能为空") String orderNo) {
            return "服务降级";
        }

        @Override
        public int addOrder(OrderDto dto) {
            return -1;
        }
    }

}
