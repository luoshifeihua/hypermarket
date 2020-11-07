package com.wqk.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    //简单交换机
    private static final String EXCHANGE_NAME="direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.链接MQ服务器
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/study");
        factory.setUsername("wqk");
        factory.setPassword("123");
        factory.setHost("192.168.91.128");
        factory.setPort(5672);
        //2.发送消息给MQ服务器
        Connection connection = factory.newConnection();
        //3.基于channel，类似会话的作用
        Channel channel = connection.createChannel();
        //4.声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //5.发布消息
        channel.basicPublish(EXCHANGE_NAME,"fruit",null,"水果".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"book",null,"书本".getBytes());
        System.out.println("发布消息成功！");
    }
}
