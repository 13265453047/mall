package com.bruce.rabbit.ttl;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rcy
 * @data 2021-08-17 21:42
 * @description 为消息设置过期时间
 */
public class TTLConsumer extends BaseRabbitMqConnect {


    public final static String TTL_EXCHANGE = "TTL_EXCHANGE";
    public final static String TTL_QUEUE = "TTL_QUEUE";
    public final static String BINDING_KEY = "TTL_KEY";

    public static void main(String[] args) throws IOException {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(TTL_EXCHANGE, BuiltinExchangeType.DIRECT.getType(), false, false, null);


        Map<String, Object> argss = new HashMap<>();
        argss.put("x-message-ttl", 30 * 1000);
        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(TTL_QUEUE, true, false, false, argss);

        // 将队列绑定到交换机上
        channel.queueBind(TTL_QUEUE, TTL_EXCHANGE, BINDING_KEY);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" Received message : '" + msg + "'");
                System.out.println(" consumerTag : " + consumerTag);
                System.out.println(" deliveryTag : " + envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        channel.basicConsume(TTL_QUEUE, consumer);
    }
}
