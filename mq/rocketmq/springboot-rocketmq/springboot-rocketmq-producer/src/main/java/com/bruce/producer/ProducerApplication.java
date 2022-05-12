package com.bruce.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        // 如下两种方式等价
//        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
//        // 第三个参数为key
//        rocketMQTemplate.syncSend("test-topic-1", "Hello, World! I'm from simple message", "18122811143034568830");
//
//        // topic: ORDER，tag: paid, cacel
//        rocketMQTemplate.convertAndSend("ORDER:paid", "Hello, World!");
//        rocketMQTemplate.convertAndSend("ORDER:cancel", "Hello, World!");
//
//        // 消息体为自定义对象
//        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));
//
//        // 发送延迟消息
//        rocketMQTemplate.sendDelayed("test-topic-1", "I'm delayed message", MessageDelayLevel.TIME_1M);
//
//        // 发送即发即失消息（不关心发送结果）
//        rocketMQTemplate.sendOneWay("test-topic-1", MessageBuilder.withPayload("I'm one way message").build());
//
//        // 发送顺序消息
//        rocketMQTemplate.syncSendOrderly("test-topic-4", "I'm order message", "1234");
//
//        // 发送异步消息
//        rocketMQTemplate.asyncSend("test-topic-1", MessageBuilder.withPayload("I'm one way message").build(), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//            }
//
//            @Override
//            public void onException(Throwable e) {
//            }
//        });
//
//        System.out.println("send finished!");
//    }
}
