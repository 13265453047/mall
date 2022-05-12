//package com.bruce.consumer;
//
///**
// * @author rcy
// * @version 1.1.0
// * @className: MyConsumer5
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
// * 配置重试次数 reconsumeTimes = 3
// */
//@Slf4j
//@Service
//@RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "my-consumer_test-topic-5",
//        consumeMode = ConsumeMode.ORDERLY, reconsumeTimes = 3)
//public class MyConsumer5 implements RocketMQListener<MessageExt> {
//    public void onMessage(MessageExt messageExt) {
//        log.info("received message: " + messageExt);
//        int a = 1 / 0;
//    }
//}
