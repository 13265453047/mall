package com.bruce.rabbit.simple.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author rcy
 * @data 2021-08-08 22:31
 * @description TODO
 */
public class FanoutProvider {

    public final static String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";


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
        Channel channel = conn.createChannel();

        // 发送消息
        String msg = "Hello world, Rabbit MQ channel";

        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish(FANOUT_EXCHANGE, "", null, msg.getBytes());
        channel.close();

        conn.close();
    }

}
