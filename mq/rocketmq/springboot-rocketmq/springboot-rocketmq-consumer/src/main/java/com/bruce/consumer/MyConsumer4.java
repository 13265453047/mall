//package com.bruce.consumer;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: MyConsumer4
// * @date 2022-05-12
// */
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.ConsumeMode;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * 顺序消息消费失败，默认不重试(官方是一直重试)
// */
//@Slf4j
//@Service
//@RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "my-consumer_test-topic-4",
//        consumeMode = ConsumeMode.ORDERLY)
//public class MyConsumer4 implements RocketMQListener<String> {
//    public void onMessage(String message) {
//        log.info("received message: " + message);
//        int a = 1 / 0;
//    }
//}
