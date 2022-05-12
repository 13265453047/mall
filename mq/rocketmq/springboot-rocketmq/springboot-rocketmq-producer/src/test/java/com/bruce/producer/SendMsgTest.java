package com.bruce.producer;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class SendMsgTest {

    @Autowired
    private RocketMQProducer producer;

    @Test
    void sendMsg() {
        producer.sendMsg("你好，中国");
    }

}
