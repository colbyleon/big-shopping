package com.jt.rabbitmq.test;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SimpleTest {

    private Connection connection; // 定义连接

    /**
     * 添加参数
     * 1. 设定主机ip
     * 2. 设定虚拟主机
     * 3. 设定端口号
     * 4. 设定用户名和密码
     * 5. 从工厂对象中获取连接
     */
    @Before
    public void init() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.88.136");
        factory.setVirtualHost("/jt");
        factory.setPort(5672);
        factory.setUsername("jtadmin");
        factory.setPassword("jtadmin");
        connection = factory.newConnection();
    }

    /**
     * 1. 获取channel对象
     * 2. 定义消息队列的名称
     * 3. 创建消息队列
     * 4. 准备发送的消息
     * 5. 发送消息
     */
    @Test
    public void provider() throws IOException {
        Channel channel = connection.createChannel();
        String queue = "simple";

        /**
         * 参数介绍
         * exclusive 表示当前队列由生产者所有，消费者不能访问
         * autoDelete: 是否自动删除 true 表示当前队列没有消息时并且没有消费者时删除队列
         * arguments: 传递的参数如果没有参数一般为null
         */
        channel.queueDeclare(queue, false, false, false, null);
        // 定义发送的消息
        String msg = "hello rabbitMQ";
        // 将消息发送
        /**
         * exchange :表示交换机,如果没有交换机采用""串
         * routingKey :路由key 如果没有路由key才用队列名称
         * props: 代表发送的参数 null
         * body: 消息的字节码文件
         */
        channel.basicPublish("",queue,null,msg.getBytes());

        // 关闭流对象
        channel.close();
        connection.close();
    }

    //定义消费者
    /**
     * 1.创建通道
     * 2.定义队列名称
     * 3.链接队列
     * 4.创建消费者对象
     * 5.从消费者对象中循环遍历获取消费内容
     * 6.将消息的字节码文件转化为串
     */
    @Test
    public void consumer() throws IOException, InterruptedException {
        //创建通道channel
        Channel channel = connection.createChannel();
        //定义队列名称 保证与生产都的队列一致
        String queue = "simple";
        //创建队列
        channel.queueDeclare(queue, false, false, false, null);
        // 创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 将消费者与队列关联
        /**
         * 参数介绍：
         * queue：队列的名称
         * autoAck: Ack表示消息队列中的消息被消费后，需要返回的确认消息
         * consumer: 消息队列中的消息被谁消费
         */
        channel.basicConsume(queue, true, consumer);
        // 循环遍历获取消息 delivery表示当前消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            byte[] body = delivery.getBody();
            String msg = new String(body, "utf-8");
            System.out.println("获取消息"+msg);
        }
    }
}
