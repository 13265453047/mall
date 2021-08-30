package com.bruce.consumer;

import com.alibaba.fastjson.JSONObject;
import com.bruce.Merchant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author rcy
 * @data 2021-08-24 21:33
 * @description TODO
 */
//@Component
//@PropertySource("classpath:mq.properties")
//@RabbitListener(queues = "${com.gupaoedu.firstQueue}", containerFactory = "rabbitListenerContainerFactory")

//@Component
//@RabbitListener(
//        bindings = {
//                @QueueBinding(
//                        value = @Queue(value = RabbitMQConstant.Queue.RECEIVE_MONEY_VOICE_QUEUE, durable = "true"),
//                        exchange = @Exchange(value = RabbitMQConstant.Exchange.RECEIVE_MONEY_VOICE_EXCHANGE),
//                        key = RabbitMQConstant.RoutingKey.RECEIVE_MONEY_VOICE_ROUTING_KEY
//                )
//        },
//        containerFactory = "rabbitListenerContainerFactory"
//)

/**
 * 设置SimpleRabbitListenerContainerFactory消息的序列化方式
 * 以Message的形式接收
 */
@PropertySource("classpath:mq.properties")
@Component
@RabbitListener(
        bindings = {
                @QueueBinding(
                        value = @Queue(value = "${com.gupaoedu.firstQueue}", durable = "true"),
                        exchange = @Exchange(value = "${com.gupaoedu.directexChange}"),
                        key = "${com.gupaoedu.routingkey}"
                )
        },
        containerFactory = "rabbitListenerContainerFactory"
)
public class FirstConsumer01 {

    @RabbitHandler
    //public void process(@Payload Merchant merchant, Channel channel, Message message) throws IOException {
    public void process(@Payload Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        final String body = new String(message.getBody(), StandardCharsets.UTF_8);
        Merchant merchant = (Merchant) JSONObject.parse(body);
        System.out.println("First Queue received msg : " + merchant);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}

