//package com.bruce.hystrix.client;
//
//import com.bruce.hystrix.service.OrderFallBackMethodApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.constraints.NotBlank;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: ApiFallBackMethodController
// * @date 2022-09-06
// */
//@RestController
//public class ApiFallBackMethodController {
//
//    @Autowired
//    private OrderFallBackMethodApi orderApi;
//
//    @GetMapping("order/fall/back/method/{orderNo}")
//    public String getOrder(@NotBlank(message = "订单编号不能为空") @PathVariable("orderNo") String orderNo) {
//        if (orderNo.equals("2")) {
//            return "success order";
//        }
//        return orderApi.order(orderNo);
//    }
//
//}
