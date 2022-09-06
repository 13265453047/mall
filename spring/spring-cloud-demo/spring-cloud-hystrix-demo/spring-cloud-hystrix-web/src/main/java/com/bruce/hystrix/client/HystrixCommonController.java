package com.bruce.hystrix.client;

import com.bruce.hystrix.service.OrderApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * HystrixCommon注解
 *
 * @author rcy
 * @version 1.1.0
 * @className: HystrixCommonController
 * @date 2022-09-06
 */
@RestController
public class HystrixCommonController {

    @Autowired
    private OrderApi orderApi;

    @HystrixCommand(
            fallbackMethod = "getOrderFallback"
    )
    @GetMapping("order/{orderNo}")
    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") String orderNo) {
        if (orderNo.equals("2")) {
            return "success order";
        }
        return orderApi.order(orderNo);
    }

    public String getOrderFallback(String orderNo) {
        return "进入 getOrderFallback -> " + orderNo;
    }

}
