package com.wqk.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    //简单队列
    private static final String QUEUE_NAME="work_queue";

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
        //4.声明队列，如果队列不存在，则会创建
        //durable:持久化，一般设置为true
        //exclusive:排他性，是否只允许当前连接使用此队列，一般设置为false
        //autoDelete:自动删除，一般设置为false
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //5.发布消息
        for (int i = 1; i <= 10; i++) {
            String msg="第"+i+"次学习打卡！";
            channel.basicPublish("", QUEUE_NAME,null,msg.getBytes());
        }
        System.out.println("发布消息成功！");
    }
}
