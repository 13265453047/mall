package com.bruce.rabbit.message;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author rcy
 * @data 2021-08-18 21:26
 * @description TODO
 */
public class MessageProducer extends BaseRabbitMqConnect {

    public static void main(String[] args) throws Exception {
        // 建立连接
        Connection conn = getConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        Map<String, Object> headers = new HashMap<>();
        headers.put("name", "gupao");
        headers.put("level", "top");

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)   // 2代表持久化
                .contentEncoding("UTF-8")  // 编码
                .expiration("50000")  // TTL，过期时间
                .headers(headers) // 自定义属性
                .priority(5) // 优先级，默认为5，配合队列的 x-max-priority 属性使用
                .messageId(String.valueOf(UUID.randomUUID()))
                .build();

        String msg = "Hello world, Rabbit MQ";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(MessageConsumer.MESSAGE_QUEUE, false, false, false, null);

        // 发送消息
        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish(MessageConsumer.MESSAGE_EXCHANGE, MessageConsumer.MESSAGE_BINDING_KEY, properties, msg.getBytes());

        channel.close();
        conn.close();
    }
}
