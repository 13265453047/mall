//package com.bruce.consumer;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: MyConsumer6
// * @date 2022-05-12
// */
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.ConsumeMode;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * 配置重试次数 reconsumeTimes = -1 代表一直重试
// */
//@Slf4j
//@Service
//@RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "my-consumer_test-topic-6",
//        consumeMode = ConsumeMode.ORDERLY, reconsumeTimes = -1)
//public class MyConsumer6 implements RocketMQListener<MessageExt> {
//    public void onMessage(MessageExt messageExt) {
//        log.info("received message: " + messageExt);
//        int a = 1 / 0;
//    }
//}