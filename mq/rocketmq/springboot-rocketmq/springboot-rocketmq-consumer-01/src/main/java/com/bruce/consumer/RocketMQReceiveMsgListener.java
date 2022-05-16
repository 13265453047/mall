package com.bruce.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
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
        // topic 主题
        topic = "receive_topic",

        // messageModel 消息模型
        // 默认值 MessageModel.CLUSTERING 集群
        // MessageModel.BROADCASTING 广播
        messageModel = MessageModel.CLUSTERING,

        // selectorType 消息选择器类型
        // 默认值 SelectorType.TAG 根据TAG选择
        // 仅支持表达式格式如：“tag1 || tag2 || tag3”，如果表达式为null或者“*”标识订阅所有消息
        // SelectorType.SQL92 根据SQL92表达式选择
        selectorType = SelectorType.TAG,

        // selectorExpression 选择器表达式
        // selectorExpression = "tagA",

        // consumerGroup 消费者分组
        consumerGroup = "receive_topic_group",

        // consumeMode 消费模式
        // 默认值 ConsumeMode.CONCURRENTLY 并行处理
        // ConsumeMode.ORDERLY 按顺序处理
        consumeMode = ConsumeMode.CONCURRENTLY,

        // consumeThreadMax 最大线程数
        //默认值 64
        consumeThreadMax = 56,

        // consumeTimeout 超时时间
        // 默认值 30000ms
        consumeTimeout = 3000,

        // nameServer 命名服务器地址
        // 默认值 ${rocketmq.name-server:}
        nameServer = "120.79.166.244:9876"
)
public class RocketMQReceiveMsgListener implements RocketMQReplyListener<String, Object> {

    @Override
    public Object onMessage(String message) {
        log.info("Listener1，接收到消息：content->{}", message);
        return "接收到数据" + message + "，已处理";
    }

}
