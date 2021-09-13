package com.bruce.rabbit.ttl;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rcy
 * @data 2021-08-17 21:42
 * @description 对于指定时间未消费的消息，进行过期处理的两种方式（也可同时使用）
 * 1）设置消息过期时间
 * 2）设置队列过期时间
 * <p>
 * 两种情况投递的消息若没有被消费，都会存在过期失效，其中以最小的时间为过期时间
 * 若我们为队列设置了死信交换价+死信队列
 * 则消息过期时，会将消息投递到对应的死信交换机，由消费死信队列的消费者进行消费过期消息
 */
public class TTLProducer extends BaseRabbitMqConnect {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        String msg = "Hello World, Rabbit MQ, DLX MSG.";

        // 1、通过队列属性设置消息过期时间
        // Map<String, Object> argss = new HashMap<>();
        // argss.put("x-message-ttl", 20000);
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        // channel.queueDeclare(TTLConsumer.TTL_QUEUE, false, false, false, argss);


        //2、 对每条消息设置过期时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2) // 持久化消息
                .contentEncoding("UTF-8")
                .expiration("10000") // TTL
                .build();

        channel.basicPublish(TTLConsumer.TTL_EXCHANGE, TTLConsumer.BINDING_KEY, properties, msg.getBytes());

        channel.close();
        connection.close();
    }
}
