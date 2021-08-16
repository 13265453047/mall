package com.bruce.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author rcy
 * @data 2021-08-16 21:29
 * @description 消费者
 */
public class FirstConsumer implements MessageListener {
    private final Logger logger = LoggerFactory.getLogger(FirstConsumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("The first cosumer received message : " + message);
    }
}
