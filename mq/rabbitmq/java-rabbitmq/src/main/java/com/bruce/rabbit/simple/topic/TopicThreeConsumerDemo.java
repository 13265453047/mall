package com.bruce.rabbit.simple.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author rcy
 * @data 2021-08-10 22:49
 * @description TODO
 */
public class TopicThreeConsumerDemo {

    public final static String TOPIC_EXCHANGE = "TOPIC_EXCHANGE";
    public final static String TOPIC_THREE_QUEUE = "TOPIC_THREE_QUEUE";

    // 若BINDING_KEY为com.*，则生产者ROUTING_KEY则为：com后有必须有一个单词才可以匹配到,如com.rabbitmq类似
    // 若BINDING_KEY为com.#，则生产者ROUTING_KEY则为：com后有的单词数量不限制（0~多个），如com.a;com.a.a等
    public final static String THREE_BINDING_KEY = "#.com";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.32.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(TOPIC_THREE_QUEUE, false, false, false, null);

        // 绑定队列和交换机
        channel.queueBind(TOPIC_THREE_QUEUE, TOPIC_EXCHANGE, THREE_BINDING_KEY);

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received message : '" + msg + "'");
                System.out.println("consumerTag : " + consumerTag);
                System.out.println("deliveryTag : " + envelope.getDeliveryTag());
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(TOPIC_THREE_QUEUE, true, consumer);
    }


}

