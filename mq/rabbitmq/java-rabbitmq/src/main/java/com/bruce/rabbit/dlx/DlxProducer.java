package com.bruce.rabbit.dlx;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author rcy
 * @data 2021-08-17 22:54
 * @description 为队列绑定死信交换机
 */
public class DlxProducer extends BaseRabbitMqConnect {


    public static void main(String[] args) throws Exception {

        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        String msg = "Hello world, Rabbit MQ, DLX MSG";

        // 设置发送的消息的属性，消息10秒钟过期
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .contentEncoding("UTF-8")
                .expiration("30000") // TTL
                .build();

        // 发送消息
        channel.basicPublish(DlxConsumer.DLX_EXCHANGE, DlxConsumer.BINDING_KEY, properties, msg.getBytes());

        channel.close();
        connection.close();
    }

}
