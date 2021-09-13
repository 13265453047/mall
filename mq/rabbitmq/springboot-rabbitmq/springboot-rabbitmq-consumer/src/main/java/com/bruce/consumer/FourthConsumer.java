package com.bruce.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author rcy
 * @data 2021-08-24 21:48
 * @description TODO
 */
@Component
@PropertySource("classpath:mq.properties")
@RabbitListener(queues = "${com.gupaoedu.fourthQueue}", containerFactory = "rabbitListenerContainerFactory")
public class FourthConsumer {
    @RabbitHandler
    public void process(String body, Channel channel, Message message) throws IOException {
        System.out.println("Fourth Queue received msg : " + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
