//package com.bruce.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: MyConsumer
// * @date 2022-05-12
// */
//
//@Slf4j
//@Service
//@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
//public class MyConsumer implements RocketMQListener<String> {
//    public void onMessage(String message) {
//        log.info("received message: " + message);
//    }
//}
