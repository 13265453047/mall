package com.bruce.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
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
@RocketMQMessageListener(
        topic = "queue_test_topic",
        messageModel = MessageModel.CLUSTERING,
        selectorType = SelectorType.TAG,
        selectorExpression = "tag1",
        consumerGroup = "queue_group_test",
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class RocketMQMsgListener2 implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        byte[] body = message.getBody();
        String msg = new String(body);
        log.info("Listener2，接收到消息：{}", msg);
    }

}
