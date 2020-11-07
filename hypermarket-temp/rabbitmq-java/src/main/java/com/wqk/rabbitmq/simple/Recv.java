package com.wqk.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    //简单队列
    private static final String QUEUE_NAME="simple_queue";

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
        //4.创建一个消费者对象
        Consumer consumer=new DefaultConsumer(channel){
            //等着队列有消息之后，自动回调
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
                String message=new String(body,"utf-8");
                System.out.println("接收消息："+message);
            }
        };
        //5.让消费者监听队列
        //autoAck：自动应答，自动告知MQ服务器，消息已被消费
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
