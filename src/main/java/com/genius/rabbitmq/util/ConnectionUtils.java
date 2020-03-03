package com.genius.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    /**
     * 获取mq的链接
     * @return connection
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //设置服务地址
        connectionFactory.setHost("127.0.0.1");

        //AMQP5672
        connectionFactory.setPort(5672);

        //vhost
        connectionFactory.setVirtualHost("/seoikei");

        //用户名
        connectionFactory.setUsername("seoikei");

        //密码
        connectionFactory.setPassword("seoikei");

        return connectionFactory.newConnection();

    }

}
