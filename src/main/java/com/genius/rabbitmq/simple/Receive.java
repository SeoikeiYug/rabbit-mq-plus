package com.genius.rabbitmq.simple;

import com.genius.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获取消息
 */
public class Receive {

    private static final String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("new api receive:" + message);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }

    private static void oldApi() throws IOException, TimeoutException, InterruptedException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("receive message" + message);
        }
    }

}
