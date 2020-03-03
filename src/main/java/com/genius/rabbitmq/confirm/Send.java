package com.genius.rabbitmq.confirm;

import com.genius.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个链接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect 将channel设置为confirm模式
        channel.confirmSelect();

        //未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleAck---multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleAck---multiple---false");
                    confirmSet.remove(deliveryTag);
                }
            }

            //handleNack
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("handleNack---multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("handleNack---multiple---false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String messageStr = "handleAck";

        while(true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, messageStr.getBytes());
            confirmSet.add(seqNo);
        }
    }

}
