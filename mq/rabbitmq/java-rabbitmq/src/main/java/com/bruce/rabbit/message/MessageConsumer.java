package com.bruce.rabbit.message;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author rcy
 * @data 2021-08-18 21:25
 * @description TODO
 */
public class MessageConsumer extends BaseRabbitMqConnect {

    public final static String MESSAGE_EXCHANGE = "MESSAGE_EXCHANGE";
    public final static String MESSAGE_QUEUE = "MESSAGE_QUEUE";
    public final static String MESSAGE_BINDING_KEY = "MESSAGE_BINDING_KEY";

    public static void main(String[] args) throws Exception {

        // 建立连接
        Connection conn = getConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(MESSAGE_EXCHANGE, BuiltinExchangeType.DIRECT, false, false, null);

        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(MESSAGE_QUEUE, false, false, false, null);

        channel.queueBind(MESSAGE_QUEUE, MESSAGE_EXCHANGE, MESSAGE_BINDING_KEY);

        System.out.println(" Waiting for message....");

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "' ");
                System.out.println("messageId : " + properties.getMessageId());
                System.out.println(properties.getHeaders().get("name") + " -- " + properties.getHeaders().get("level"));
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(MESSAGE_QUEUE, true, consumer);
    }

}
