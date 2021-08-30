package com.bruce.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author rcy
 * @data 2021-08-24 21:09
 * @description TODO
 */
@Configuration
//@PropertySource("classpath:mq.properties")
@PropertySource("classpath:mq-${spring.profiles.active}.properties")
public class RabbitMqConfig {

    /*@Value("${com.gupaoedu.firstQueue}")
    private String firstQueue;

    @Value("${com.gupaoedu.secondQueue}")
    private String secondQueue;

    @Value("${com.gupaoedu.thirdQueue}")
    private String thirdQueue;

    @Value("${com.gupaoedu.fourthQueue}")
    private String fourthQueue;

    @Value("${com.gupaoedu.directexChange}")
    private String directExchange;

    @Value("${com.gupaoedu.topicexChange}")
    private String topicExchange;

    @Value("${com.gupaoedu.fanoutexChange}")
    private String fanoutExchange;*/

    @Resource
    private RabbitMqSource mqSource;


    /**
     * 创建四个队列
     */

    @Bean("vipFirstQueue")
    public Queue getFirstQueue() {
//        return new Queue(firstQueue);
        return new Queue(mqSource.getFirstQueue());
    }

    @Bean("vipSecondQueue")
    public Queue getSecondQueue() {
//        return new Queue(secondQueue);
        return new Queue(mqSource.getSecondQueue());
    }

    @Bean("vipThirdQueue")
    public Queue getThirdQueue() {
//        return new Queue(thirdQueue);
        return new Queue(mqSource.getThirdQueue());
    }

    @Bean("vipFourthQueue")
    public Queue getFourthQueue() {
//        return new Queue(fourthQueue);
        return new Queue(mqSource.getFourthQueue());
    }

    /**
     * 创建三个交换机
     */
    @Bean("vipDirectExchange")
    public DirectExchange getDirectExchange() {
//        return new DirectExchange(directExchange);
        return new DirectExchange(mqSource.getDirectExchange());
    }

    @Bean("vipTopicExchange")
    public TopicExchange getTopicExchange() {
//        return new TopicExchange(topicExchange);
        return new TopicExchange(mqSource.getTopicExchange());
    }

    @Bean("vipFanoutExchange")
    public FanoutExchange getFanoutExchange() {
//        return new FanoutExchange(fanoutExchange);
        return new FanoutExchange(mqSource.getFanoutExchange());
    }

    /**
     * 定义四个绑定关系
     */
    @Bean
    public Binding bindFirst(@Qualifier("vipFirstQueue") Queue queue, @Qualifier("vipDirectExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("gupao.best");
    }

    @Bean
    public Binding bindSecond(@Qualifier("vipSecondQueue") Queue queue, @Qualifier("vipTopicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.gupao.*");
    }

    @Bean
    public Binding bindThird(@Qualifier("vipThirdQueue") Queue queue, @Qualifier("vipFanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFourth(@Qualifier("vipFourthQueue") Queue queue, @Qualifier("vipFanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 在消费端转换JSON消息
     * 监听类都要加上containerFactory属性
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        // AcknowledgeMode.MANUAL 手动ack
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setAutoStartup(true);
        return factory;
    }

    /*private static final int DEFAULT_CONCURRENT = 1, DEFAULT_MAX_CONCURRENT = 10, DEFAULT_PREFETCH_COUNT = 15;

    @Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setPrefetchCount(DEFAULT_PREFETCH_COUNT);
        containerFactory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        containerFactory.setMaxConcurrentConsumers(DEFAULT_MAX_CONCURRENT);
        containerFactory.setDefaultRequeueRejected(false);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setMessageConverter(new MessageConverter() {

            @Override
            public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
                return new Message(o.toString().getBytes(StandardCharsets.UTF_8), messageProperties);
            }

            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                return message;
            }
        });
        configurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }*/


}
