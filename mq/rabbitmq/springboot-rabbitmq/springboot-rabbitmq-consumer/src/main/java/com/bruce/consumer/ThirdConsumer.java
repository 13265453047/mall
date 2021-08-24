package com.bruce.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author rcy
 * @data 2021-08-24 21:48
 * @description TODO
 */
@Component
@PropertySource("classpath:mq.properties")
@RabbitListener(queues = "${com.gupaoedu.thirdQueue}", containerFactory = "rabbitListenerContainerFactory")
public class ThirdConsumer {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("Third Queue received msg : " + msg);
    }
}
