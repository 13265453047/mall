package com.bruce.rabbit.simple.fanout;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author rcy
 * @data 2021-08-08 22:31
 * @description TODO
 */
public class FanoutOneConsumer {

    public final static String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
    public final static String FANOUT_ONE_QUEUE = "FANOUT_ONE_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("192.168.32.128");
        // 默认监听端口
        factory.setPort(5672);
        // 虚拟机
        factory.setVirtualHost("/");

        // 设置访问的用户
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, false, false, null);

        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(FANOUT_ONE_QUEUE, false, false, false, null);
        System.out.println(" Waiting for message....");

        // 绑定队列和交换机
        channel.queueBind(FANOUT_ONE_QUEUE, FANOUT_EXCHANGE, "");

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("consumer Received message : '" + msg + "'");
                System.out.println("consumer consumerTag : " + consumerTag);
                System.out.println("consumer deliveryTag : " + envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(FANOUT_ONE_QUEUE, true, consumer);
    }

}
