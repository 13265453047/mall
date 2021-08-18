package com.bruce.rabbit.ack;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-08-18 20:35
 * @description 消息消费者，用于测试消费者手工应答和重回队列
 */
public class AckConsumer extends BaseRabbitMqConnect {


    public final static String ACK_EXCHANGE = "ACK_EXCHANGE";
    public final static String ACK_QUEUE = "ACK_QUEUE";
    public final static String ACK_BINDING_KEY = "ACK_BINDING_KEY";


    public static void main(String[] args) throws Exception {
        // 简历连接
        Connection connection = getConnection();
        // 创建消息通道
        Channel channel = connection.createChannel();

        // 声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(ACK_EXCHANGE, BuiltinExchangeType.DIRECT, true, false, null);

        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(ACK_QUEUE, true, false, false, null);

        channel.queueBind(ACK_QUEUE, ACK_EXCHANGE, ACK_BINDING_KEY);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("===DeliveryTag=" + envelope.getDeliveryTag() + "===" + msg);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 拒绝消息
                // requeue：是否重新入队列，true：是；false：直接丢弃，相当于告诉队列可以直接删除掉
                // TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
                // channel.basicReject(envelope.getDeliveryTag(), true);

                // 批量拒绝
                // requeue：是否重新入队列
                // TODO 如果只有这一个消费者，requeue 为true 的时候会造成消息重复消费
                channel.basicNack(envelope.getDeliveryTag(), false, true);

                // 手工应答
                // 如果不应答，队列中的消息会一直存在，重新连接的时候会重复消费
                // multiple:是否批量处理
                // channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 开始获取消息，注意这里开启了手工应答
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(ACK_QUEUE, false, consumer);

    }

}
