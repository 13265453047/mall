package com.bruce.hystrix.client;

import com.bruce.hystrix.service.OrderApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rcy
 * @version 1.1.0
 * @className: OrderController
 * @date 2022-09-06
 */
@RestController
public class OrderClient implements OrderApi {

    @Override
    public String order(String orderNo) {
        return "OrderNo: " + orderNo;
    }
}
