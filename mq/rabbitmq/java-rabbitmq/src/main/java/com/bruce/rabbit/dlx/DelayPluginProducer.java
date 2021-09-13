package com.bruce.rabbit.dlx;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rcy
 * @data 2021-08-17 22:54
 * @description 为队列绑定死信交换机
 */
public class DelayPluginProducer extends BaseRabbitMqConnect {

    /**
     * 允许最大时长 30 秒
     */
    public static final Integer DELAY_MSG = 30 * 1000;

    public static void main(String[] args) throws Exception {

        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        String msg = "Hello world, Rabbit MQ, DLX MSG";

        Map<String, Object> headers = new HashMap<>();
        headers.put("x-delay", DELAY_MSG);

        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder()
                .headers(headers);

        // 发送消息
        channel.basicPublish(DelayPluginConsumer.DELAY_PLUGIN_EXCHANGE, DelayPluginConsumer.DELAY_PLUGIN_BINDING_KEY, props.build(), msg.getBytes());

        channel.close();
        connection.close();
    }

}
