package com.bruce.rabbit.transaction;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author rcy
 * @data 2021-08-18 21:36
 * @description 消息生产者，测试事务模式。发送消息的效率比较低，建议使用Confirm模式
 * 参考文章：https://www.cnblogs.com/vipstone/p/9350075.html
 */
public class TransactionProducer extends BaseRabbitMqConnect {
    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {

        // 建立连接
        Connection conn = getConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        String msg = "Hello world, Rabbit MQ";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        try {
            channel.txSelect();
            // 发送消息，发布了4条，但只确认了3条
            // String exchange, String routingKey, BasicProperties props, byte[] body
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            int i = 1 / 0;
            channel.txCommit();
            channel.basicPublish("", QUEUE_NAME, null, (msg).getBytes());
            channel.txCommit();
            System.out.println("消息发送成功");
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("消息已经回滚");
        }

        channel.close();
        conn.close();
    }
}
