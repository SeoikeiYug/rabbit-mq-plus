package com.genius.rabbitmq.simple;

import com.genius.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 耦合性高
 * 生产者消费者一一对应
 * 多个消费者不支持
 * 队列名变更则需要同时变更
 */
public class Send {

    private static final String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "hello simple";

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        System.out.println("---send message---" + message);
        channel.close();
        connection.close();
    }
}
