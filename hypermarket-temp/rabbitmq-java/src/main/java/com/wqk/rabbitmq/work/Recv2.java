package com.wqk.rabbitmq.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
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
        final Channel channel = connection.createChannel();
        //设置一次只接收一个消息
        channel.basicQos(1);
        //4.创建一个消费者对象
        Consumer consumer=new DefaultConsumer(channel){
            //等着队列有消息之后，自动回调
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
                String message=new String(body,"utf-8");
                System.out.println("Recv2---接收消息："+message);
                //刚好处理的比较慢
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //手动回复，消息处理完毕
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //5.让消费者监听队列
        //autoAck：自动应答，自动告知MQ服务器，消息已被消费
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
