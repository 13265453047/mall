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
public class AckConsumer2 extends BaseRabbitMqConnect {

    public static void main(String[] args) throws Exception {
        // 简历连接
        Connection connection = getConnection();
        // 创建消息通道
        Channel channel = connection.createChannel();


        // 声明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(AckConsumer.ACK_QUEUE, true, false, false, null);

        channel.queueBind(AckConsumer.ACK_QUEUE, AckConsumer.ACK_EXCHANGE, AckConsumer.ACK_BINDING_KEY);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("===DeliveryTag=" + envelope.getDeliveryTag() + "===" + msg);


                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 手工应答
                // 如果不应答，队列中的消息会一直存在，重新连接的时候会重复消费
                // multiple:是否批量处理
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 开始获取消息，注意这里开启了手工应答
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(AckConsumer.ACK_QUEUE, false, consumer);

    }

}
