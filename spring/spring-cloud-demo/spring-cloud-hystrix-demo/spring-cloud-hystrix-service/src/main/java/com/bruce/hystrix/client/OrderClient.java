package com.bruce.hystrix.client;

import com.bruce.hystrix.dto.OrderDto;
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

    @Override
    public int addOrder(OrderDto dto) {
        System.out.println("新增订单：" + dto.toString());
        return 1;
    }

}
