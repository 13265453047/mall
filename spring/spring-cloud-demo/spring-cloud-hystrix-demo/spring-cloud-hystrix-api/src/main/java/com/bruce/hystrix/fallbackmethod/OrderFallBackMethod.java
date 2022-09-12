package com.bruce.hystrix.fallbackmethod;

import com.bruce.hystrix.service.OrderFallBackMethodApi;
import org.springframework.stereotype.Component;

/**
 * @author rcy
 * @version 1.1.0
 * @className: OrderFallBackMethod
 * @date 2022-09-06
 */
@Component
public class OrderFallBackMethod implements OrderFallBackMethodApi {

    @Override
    public String orderFallBackMethod(String orderNo) {
        return "进入 Api -> OrderFallBackMethod: " + orderNo;
    }

}
