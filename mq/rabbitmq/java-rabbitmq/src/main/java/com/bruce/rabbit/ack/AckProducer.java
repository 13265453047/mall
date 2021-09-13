package com.bruce.rabbit.ack;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author rcy
 * @data 2021-08-18 20:35
 * @description 消息生产者，用于测试消费者手工应答和重回队列
 */
public class AckProducer extends BaseRabbitMqConnect {

    public static void main(String[] args) throws Exception {
        // 简历连接
        Connection connection = getConnection();
        // 创建消息通道
        Channel channel = connection.createChannel();

        String msg = "test ack message ";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(AckConsumer.ACK_EXCHANGE, AckConsumer.ACK_BINDING_KEY, null, (msg + i).getBytes());
        }

        channel.close();
        connection.close();
    }

}
