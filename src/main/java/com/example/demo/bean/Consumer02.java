package com.example.demo.bean;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer02 {
    public static final String QUEUE_NAME = "ps_consumer02";
    public static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //1.创建connectionFactory
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("8.129.218.89");
            connectionFactory.setVirtualHost("/");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("ActSpirits");
            connectionFactory.setPassword("Scwm,dnyx0");
            //2.创建connection
            connection = connectionFactory.newConnection();
            //3.创建信道
            channel = connection.createChannel();
            //最大一次收一个
            channel.basicQos(1);
            //4.指定队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //5.将队列绑定到消息交换机并指定队列direct为"q1"
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"q1");
            //6.接收消息
            Channel finalChannel = channel;
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Consumer02" + message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //手动回执消息
                    finalChannel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                }
            };
            //6.监听指定队列
            //设置手动回执消息才能实现公平分发
            Boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
