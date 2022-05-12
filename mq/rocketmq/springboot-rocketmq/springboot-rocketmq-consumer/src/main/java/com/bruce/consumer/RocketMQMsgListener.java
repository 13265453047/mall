package com.bruce.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author rcy
 * @version 1.1.0
 * @className: RocketMQMsgListener
 * @date 2022-05-12
 */

/**
 * rocketmq 消息监听，@RocketMQMessageListener中的selectorExpression为tag，默认为 *
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "queue_test_topic", selectorExpression = "", consumerGroup = "queue_group_test")
public class RocketMQMsgListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("接收到消息：{}", msg);
    }

}
