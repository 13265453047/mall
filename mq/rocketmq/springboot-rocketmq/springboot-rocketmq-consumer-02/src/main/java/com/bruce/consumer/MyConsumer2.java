//package com.bruce.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.springframework.stereotype.Service;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: MyConsumer2
// * @date 2022-05-12
// */
//
//@Slf4j
//@Service
//@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
//public class MyConsumer2 implements RocketMQListener<OrderPaidEvent> {
//    public void onMessage(OrderPaidEvent orderPaidEvent) {
//        log.info("received orderPaidEvent: " + orderPaidEvent);
//    }
//}
