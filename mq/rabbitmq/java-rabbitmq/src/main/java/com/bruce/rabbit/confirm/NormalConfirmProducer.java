package com.bruce.rabbit.confirm;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author rcy
 * @data 2021-08-18 22:11
 * @description TODO
 */
public class NormalConfirmProducer extends BaseRabbitMqConnect {
    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {

        // 建立连接
        Connection conn = getConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        String msg = "Hello world, Rabbit MQ ,Normal Confirm";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 开启发送方确认模式
        channel.confirmSelect();

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        // 普通Confirm，发送一条，确认一条
        if (channel.waitForConfirms()) {
            System.out.println("消息发送成功");
        } else {
            System.out.println("消息发送失败");
        }

        channel.close();
        conn.close();
    }
}
