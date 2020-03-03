package com.genius.rabbitmq.route;

import com.genius.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //发送消息
        String message = "hello direct";

        String routingKey = "error";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());

        System.out.println("Send :" + message);

        channel.close();
        connection.close();
    }

}
