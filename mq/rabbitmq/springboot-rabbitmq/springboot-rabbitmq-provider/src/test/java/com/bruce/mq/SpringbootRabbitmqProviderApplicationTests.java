package com.bruce.mq;

import com.bruce.mq.producer.RabbitSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 记得一定要先启动消费者，否则交换机和队列以及绑定关系都不会创建
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootRabbitmqProviderApplicationTests {

    @Autowired
    RabbitSender rabbitSender;

    @Test
    void contextLoads() throws JsonProcessingException {
        // 先启动消费者 consumer，否则交换机、队列、绑定都不存在
        rabbitSender.send();
    }

}
