package com.bruce.hystrix.controller;

import com.bruce.hystrix.service.OrderFallBackMethodApi;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author rcy
 * @version 1.1.0
 * @className: OrderFallBackMethodClient
 * @date 2022-09-06
 */
@RestController
public class OrderFallBackMethodClient implements OrderFallBackMethodApi {

    @Override
    public String orderFallBackMethod(@NotBlank(message = "订单编号不能为空") String orderNo) {
        try {
            // Hystrix 超时降级默认是 1s中，这里睡眠 2s 中，故会降级
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Success OrderNo: " + orderNo;
    }

}
