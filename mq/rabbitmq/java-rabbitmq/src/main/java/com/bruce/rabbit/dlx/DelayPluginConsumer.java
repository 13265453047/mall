package com.bruce.rabbit.dlx;

import com.bruce.rabbit.BaseRabbitMqConnect;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rcy
 * @data 2021-08-17 22:54
 * @description 使用延时插件实现的消息投递-消费者
 * 必须要在服务端安装rabbitmq-delayed-message-exchange插件，安装步骤见README.MD
 * 先启动消费者
 */
public class DelayPluginConsumer extends BaseRabbitMqConnect {

    public final static String DELAY_PLUGIN_EXCHANGE = "DELAY_PLUGIN_EXCHANGE";
    public final static String DELAY_PLUGIN_QUEUE = "DELAY_PLUGIN_QUEUE";
    public final static String DELAY_PLUGIN_BINDING_KEY = "DELAY_PLUGIN_BINDING_KEY";


    public static void main(String[] args) throws Exception {

        Connection connection = getConnection();
        Channel channel = connection.createChannel();

        // 声明x-delayed-message类型的exchange
        Map<String, Object> argss = new HashMap<>();
        argss.put("x-delayed-type", "direct");
        channel.exchangeDeclare(DELAY_PLUGIN_EXCHANGE, "x-delayed-message", false, false, argss);


        // 声明普通队列，并设置对应的死信交换机参数
        channel.queueDeclare(DELAY_PLUGIN_QUEUE, false, false, false, null);

        // 绑定
        channel.queueBind(DELAY_PLUGIN_QUEUE, DELAY_PLUGIN_EXCHANGE, DELAY_PLUGIN_BINDING_KEY);

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                System.out.println("收到消息：[" + msg + "]\n接收时间：" + sf.format(new Date()));
            }
        };

        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(DELAY_PLUGIN_QUEUE, true, consumer);
    }

}
