package com.bruce.producer;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProducerApplicationTests {

    @Autowired
    private RocketMQProducer producer;

    @Test
    void contextLoads() {
    }

    @Test
    public void sendMsg() {
        int count = 10;
        while (count > 0) {
            count--;
            producer.sendMsg("你好，中国");
        }
    }

    @Test
    public void sendAsyncMsg() {
        int count = 5;
        while (count > 0) {
            count--;
            producer.sendAsyncMsg("你好，中国");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendDelayMsg() {
        int count = 5;
        while (count > 0) {
            count--;
            producer.sendDelayMsg("你好，中国", 2);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendTagMsg() {
        int count = 5;
        while (count > 0) {
            count--;
            producer.sendTagMsg("你好，中国");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
