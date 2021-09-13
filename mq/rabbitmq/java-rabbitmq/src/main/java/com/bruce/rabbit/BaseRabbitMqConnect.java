package com.bruce.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author rcy
 * @data 2021-08-17 21:44
 * @description 建立连接父类
 */
public class BaseRabbitMqConnect {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();

            // 连接IP
            factory.setHost("192.168.32.128");
            // 默认监听端口
            factory.setPort(5672);
            // 虚拟机
            factory.setVirtualHost("/");

            // 设置访问的用户
            factory.setUsername("admin");
            factory.setPassword("admin");
            // 建立连接
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
