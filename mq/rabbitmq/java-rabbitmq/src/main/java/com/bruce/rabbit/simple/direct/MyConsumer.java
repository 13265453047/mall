package com.bruce.rabbit.simple.direct;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author rcy
 * @data 2021-08-08 22:31
 * @description TODO
 */
public class MyConsumer {

    public final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";
    public final static String QUEUE_NAME = "SIMPLE_QUEUE";

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
        Channel channel1 = conn.createChannel();

        // 声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel1.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, null);

        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel1.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" Waiting for message....");

        // 绑定队列和交换机
        channel1.queueBind(QUEUE_NAME, EXCHANGE_NAME, "gupao.best");

        // 创建消费者
        Consumer consumer1 = new DefaultConsumer(channel1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("consumer1 Received message : '" + msg + "'");
                System.out.println("consumer1 consumerTag : " + consumerTag);
                System.out.println("consumer1 deliveryTag : " + envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel1.basicConsume(QUEUE_NAME, true, consumer1);


        // ========================channel2==========================

        Channel channel2 = conn.createChannel();
        // 声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel2.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, null);

        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel2.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" Waiting for message....");

        // 绑定队列和交换机
        channel2.queueBind(QUEUE_NAME, EXCHANGE_NAME, "gupao.best");

        // 创建消费者
        Consumer consumer2 = new DefaultConsumer(channel2) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("consumer2 Received message : '" + msg + "'");
                System.out.println("consumer2 consumerTag : " + consumerTag);
                System.out.println("consumer2 deliveryTag : " + envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel2.basicConsume(QUEUE_NAME, true, consumer2);
    }

}
