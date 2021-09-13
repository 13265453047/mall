package com.bruce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author rcy
 * @data 2021-08-24 21:09
 * @description TODO
 */
@Data
//@Configuration
@ConfigurationProperties(prefix = "com.gupaoedu")
public class RabbitMqSource {

    private String firstQueue;

    private String secondQueue;

    private String thirdQueue;

    private String fourthQueue;

    private String directExchange;

    private String topicExchange;

    private String fanoutExchange;


}
