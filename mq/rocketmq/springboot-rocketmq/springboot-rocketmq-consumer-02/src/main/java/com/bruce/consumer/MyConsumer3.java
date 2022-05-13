//package com.bruce.consumer;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: MyConsumer3
// * @date 2022-05-12
// */
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * 指定连接某个MQ集群
// */
//
//@Slf4j
//@Service
//@RocketMQMessageListener(nameServer = "127.0.0.1:9877", instanceName = "tradeCluster", topic = "test-topic-3", consumerGroup = "my-consumer_test-topic-3")
//public class MyConsumer3 implements RocketMQListener<String> {
//    public void onMessage(String message) {
//        log.info("received message: " + message);
//    }
//}
