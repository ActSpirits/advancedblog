package com.example.demo.bean;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        try {
            //1.创建connection工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("8.129.218.89");
            connectionFactory.setVirtualHost("/");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("ActSpirits");
            connectionFactory.setPassword("Scwm,dnyx0");
            //2.创建connection对象
            connection = connectionFactory.newConnection();
            //3.创建信道channel
            channel = connection.createChannel();
            //4.创建exchange
            //选择交换机模式为topic exchange
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");
            //5.发送消息到指定队列
            String message = "topic";
            //第二个参数指定队列direct为warning
            channel.basicPublish(EXCHANGE_NAME,"q1.q2",null,message.getBytes("utf-8"));
            System.out.println("消息发送成功!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (null != channel && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
