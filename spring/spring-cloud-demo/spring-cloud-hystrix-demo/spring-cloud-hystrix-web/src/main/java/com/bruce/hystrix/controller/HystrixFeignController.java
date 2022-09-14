package com.bruce.hystrix.controller;

import com.bruce.hystrix.dto.OrderDto;
import com.bruce.hystrix.service.OrderApi;
import com.bruce.hystrix.service.OrderFallBackMethodApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 通过 openFign
 *
 * @author rcy
 * @version 1.1.0
 * @className: HystrixFeignController
 * @date 2022-09-06
 */
@RestController
@RequestMapping("hystrix")
public class HystrixFeignController {

    @Autowired
    private OrderFallBackMethodApi orderFallBackMethodApi;
    @Autowired
    private OrderApi orderApi;

    @GetMapping("feign/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") String orderNo) {
        if (orderNo.equals("2")) {
            return "success order";
        }
        return orderFallBackMethodApi.orderFallBackMethod(orderNo);
    }

    @PostMapping("feign/add")
    public String addOrder() {
        OrderDto dto = OrderDto.builder()
                .orderNo("11111")
                .desc("新增订单")
                .build();
        return orderApi.addOrder(dto) > 0 ? "SUCCESS" : "FAILURE";
    }

}
