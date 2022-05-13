package com.bruce.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author rcy
 * @version 1.1.0
 * @className: RocketMQProducer
 * @date 2022-05-12
 */

/**
 * 使用RocketMQ发送三种类型的消息：同步消息、异步消息和单向消息。其中前两种消息是可靠的，因为会有发送是否成功的应答。
 * 使用RocketMQ来消费接收到的消息。
 */
@Slf4j
@Service
public class RocketMQProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

//    @Value("${rocketmq.producer.send-message-timeout}")
//    private Integer messageTimeOut;


    /**
     * 发送普通消息
     */
    public void send(String msgBody) {
        //send spring message
        rocketMQTemplate.send("queue_test_topic", MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送普通消息
     */
    public void sendOrder(String topic, String msgBody) {
        //send spring message
        rocketMQTemplate.send(topic, MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送普通消息
     */
    public void sendKey(String topic, String key, String tag, String msgBody) {
        //send spring message
        topic = topic + ":" + tag;
        Message<String> msg = MessageBuilder.withPayload(msgBody).setHeader(RocketMQHeaders.KEYS, key).build();
        rocketMQTemplate.send(topic, msg);
    }

    /**
     * 发送普通消息
     */
    public void sendMsg(String msgBody) {
        rocketMQTemplate.syncSend("queue_test_topic", MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送同步消息
     *
     * @param msgBody
     */
    public void sendConvertAndSend(String msgBody) {
        //send message synchronously
        rocketMQTemplate.convertAndSend("queue_test_topic", MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送异步消息 在SendCallback中可处理相关成功失败时的逻辑
     */
    public void sendAsyncMsg(String msgBody) {
        //send messgae asynchronously
        rocketMQTemplate.asyncSend("queue_test_topic", MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
                log.info("发送异步消息，处理消息发送成功逻辑");
            }

            @Override
            public void onException(Throwable e) {
                // 处理消息发送异常逻辑
                log.info("发送异步消息，处理消息发送异常逻辑");
            }
        });
    }

    /**
     * 发送延时消息<br/>
     * 在start版本中 延时消息一共分为18个等级分别表示的延迟时间为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h<br/>
     */
    public void sendDelayMsg(String msgBody, Integer delayLevel) {
        int messageTimeOut = 3000;
        rocketMQTemplate.syncSend("queue_test_topic", MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
    }

    /**
     * 发送带tag的消息,直接在topic后面加上":tag"
     */
    public void sendTagMsg(String msgBody) {
        rocketMQTemplate.syncSend("queue_test_topic:tag1", MessageBuilder.withPayload(msgBody).build());
    }

    /**
     * 发送顺序消息
     *
     * @param msgBody
     */
    public void syncSendOrderly(String msgBody) {
        //Send messages orderly
        rocketMQTemplate.syncSendOrderly("orderly_topic", MessageBuilder.withPayload(msgBody).build(), "orderId");
    }


}
