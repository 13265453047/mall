package com.bruce.rabbit.simple.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author rcy
 * @data 2021-08-08 22:31
 * @description TODO
 */
public class MyProvider {

    public final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("192.168.32.128");
        // 连接端口
        factory.setPort(5672);
        // 虚拟机
        factory.setVirtualHost("/");
        // 用户
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel1 = conn.createChannel();

        // 发送消息
        String msg1 = "Hello world, Rabbit MQ channel1";

        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel1.basicPublish(EXCHANGE_NAME, "gupao.best", null, msg1.getBytes());
        channel1.close();


        Channel channel2 = conn.createChannel();
        String msg2 = "Hello world, Rabbit MQ channel2";

        channel2.basicPublish(EXCHANGE_NAME, "gupao.best", null, msg2.getBytes());
        channel2.close();


        Channel channel3 = conn.createChannel();
        String msg3 = "Hello world, Rabbit MQ channel3";

        channel3.basicPublish(EXCHANGE_NAME, "gupao.best", null, msg3.getBytes());
        channel3.close();



        conn.close();
    }

}
