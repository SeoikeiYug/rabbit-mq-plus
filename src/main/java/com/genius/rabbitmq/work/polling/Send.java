package com.genius.rabbitmq.work.polling;

import com.genius.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "WORK_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 50; i++) {

            String message = "hello" + i;

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            Thread.sleep(i * 20);

        }
        channel.close();
        connection.close();
    }

}
