package com.bruce.rabbit.simple;

import com.bruce.rabbit.simple.fanout.FanoutOneConsumer;
import com.bruce.rabbit.simple.fanout.FanoutThreeConsumer;
import com.bruce.rabbit.simple.fanout.FanoutTwoConsumer;
import com.bruce.rabbit.simple.direct.MyConsumer;
import com.bruce.rabbit.simple.topic.TopicOneConsumerDemo;
import com.bruce.rabbit.simple.topic.TopicThreeConsumerDemo;
import com.bruce.rabbit.simple.topic.TopicTwoConsumerDemo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author rcy
 * @data 2021-08-10 22:45
 * @description TODO
 */
public class DeleteTestAllData {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("192.168.32.128");
        // 连接端口
        factory.setPort(5672);
        // 虚拟机
        factory.setVirtualHost("/");
        // 用户
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        String[] queueNames = {
                MyConsumer.QUEUE_NAME,
                TopicOneConsumerDemo.TOPIC_ONE_QUEUE,
                TopicTwoConsumerDemo.TOPIC_TWO_QUEUE,
                TopicThreeConsumerDemo.TOPIC_THREE_QUEUE,

                FanoutOneConsumer.FANOUT_ONE_QUEUE,
                FanoutTwoConsumer.FANOUT_TWO_QUEUE,
                FanoutThreeConsumer.FANOUT_THREE_QUEUE

        };


        String[] exchangeNames = {
                MyConsumer.EXCHANGE_NAME,
                TopicOneConsumerDemo.TOPIC_EXCHANGE,
                FanoutThreeConsumer.FANOUT_EXCHANGE
        };

        for (String queueName : queueNames) {
            channel.queueDelete(queueName);
        }

        for (String exchangeName : exchangeNames) {
            channel.exchangeDelete(exchangeName);
        }

        channel.close();
        conn.close();

    }


}
