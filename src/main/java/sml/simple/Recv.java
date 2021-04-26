package sml.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Recv {

    private static String QUEUE_NAME = "javaForQueue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        // 这里声明队列是为了防止先启动send，导致队列不存在报错
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("waiting from javaForQueue queue message...");

        channel.basicConsume(QUEUE_NAME, (consumerTag, delivery) -> {
            System.out.println("recived message=>" + new String(delivery.getBody()));
        }, consumerTag -> {
            System.out.println("recived message error!");
        });

    }
}
