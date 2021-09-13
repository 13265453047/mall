package com.bruce;

import com.bruce.config.RabbitMqSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties({RabbitMqSource.class})
@SpringBootApplication
public class SpringbootRabbitmqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmqConsumerApplication.class, args);
    }

}
