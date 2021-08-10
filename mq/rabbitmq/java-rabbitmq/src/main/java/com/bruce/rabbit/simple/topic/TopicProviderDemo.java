package com.bruce.rabbit.simple.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author rcy
 * @data 2021-08-10 22:50
 * @description TODO
 */
public class TopicProviderDemo {

    public final static String TOPIC_EXCHANGE = "TOPIC_EXCHANGE";
    public final static String ONE_ROUTE_KEY = "com.ra";
    public final static String TWO_ROUTE_KEY = "com.ra.ra";
    public final static String THREE_ROUTE_KEY = "ra.com";


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
        String msg = "Hello world, Rabbit MQ";

        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish(TOPIC_EXCHANGE, THREE_ROUTE_KEY, null, msg.getBytes());

        channel.close();
        conn.close();
    }

}
