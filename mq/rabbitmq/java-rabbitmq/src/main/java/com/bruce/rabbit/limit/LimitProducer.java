package com.bruce.rabbit.limit;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.concurrent.TimeUnit;

/**
 * @author rcy
 * @data 2021-08-18 21:45
 * @description 消息生产者，在启动消费者之后再启动
 * 用于测试消费者限流
 */
public class LimitProducer extends BaseRabbitMqConnect {
    private final static String QUEUE_NAME = "TEST_LIMIT_QUEUE";

    public static void main(String[] args) throws Exception {

        // 建立连接
        Connection conn = getConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        String msg = "a limit message ";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        // String exchange, String routingKey, BasicProperties props, byte[] body
        for (int i = 0; i < 100; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicPublish("", QUEUE_NAME, null, (msg + i).getBytes());
        }

        channel.close();
        conn.close();
    }
}
