package com.bruce.rabbit.dlx;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rcy
 * @data 2021-08-17 22:54
 * @description 为队列绑定死信交换机
 */
public class DlxConsumer extends BaseRabbitMqConnect {

    // 普通
    public final static String DLX_EXCHANGE = "DLX_EXCHANGE";
    public final static String DLX_QUEUE = "DLX_QUEUE";


    /**
     * 当消息过期时，会投递到设置的死信交换机，死信交换机可以绑定多个死信队列，具体路由到那个私信队列？
     * 需要根据消息生产者的RoutingKey与哪个死信队列的BindingKey匹配
     */
    public final static String BINDING_KEY = "DLX_BINDING_KEY";

    // 死信
    public final static String DEAD_LETTER_EXCHANGE = "DEAD_LETTER_EXCHANGE";
    public final static String DEAD_LETTER_QUEUE = "DEAD_LETTER_QUEUE";


    public static void main(String[] args) throws Exception {

        Connection connection = getConnection();

        Channel channel1 = connection.createChannel();


        // 指定队列的死信交换机
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);

        // 声明普通队列，并设置对应的死信交换机参数
        channel1.queueDeclare(DLX_QUEUE, true, false, false, arguments);
        // 声明普通交换机
        channel1.exchangeDeclare(DLX_EXCHANGE, BuiltinExchangeType.DIRECT.getType(), false, false, false, null);
        // 绑定
        channel1.queueBind(DLX_QUEUE, DLX_EXCHANGE, BINDING_KEY);


        Channel channel2 = connection.createChannel();

        // 声明死信交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel2.exchangeDeclare(DEAD_LETTER_EXCHANGE, BuiltinExchangeType.DIRECT.getType(), false, false, false, null);
        // 声明死信队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel2.queueDeclare(DEAD_LETTER_QUEUE, false, false, false, null);
        // 绑定
        channel2.queueBind(DEAD_LETTER_QUEUE, DEAD_LETTER_EXCHANGE, BINDING_KEY);

        Consumer consumer = new DefaultConsumer(channel2) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" Received message : '" + msg + "'");
                System.out.println(" consumerTag : " + consumerTag);
                System.out.println(" deliveryTag : " + envelope.getDeliveryTag());
            }
        };

        // 为死信队列指定消费者
        channel2.basicConsume(DEAD_LETTER_QUEUE, consumer);
    }

}
