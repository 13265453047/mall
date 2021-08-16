package com.bruce.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author rcy
 * @data 2021-08-16 21:31
 * @description TODO
 */
public class SecondConsumer implements MessageListener {
    private final Logger logger = LoggerFactory.getLogger(SecondConsumer.class);


    @Override
    public void onMessage(Message message) {
        logger.info("The second cosumer received message : " + message);

    }
}
