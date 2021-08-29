package com.bruce.consumer;

import com.bruce.Merchant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author rcy
 * @data 2021-08-24 21:33
 * @description TODO
 */
@Component
@PropertySource("classpath:mq.properties")
@RabbitListener(queues = "${com.gupaoedu.firstQueue}", containerFactory = "rabbitListenerContainerFactory")
public class FirstConsumer {

    @RabbitHandler
    public void process(@Payload Merchant merchant, Channel channel, Message message) throws IOException {
        System.out.println("First Queue received msg : " + merchant.getName());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}

