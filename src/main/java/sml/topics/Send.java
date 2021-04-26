package sml.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private static String EXCHANGE_NAME = "javaForTopicExchange";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            // 发送消息
            String message = "hello topic exchange";

            channel.basicPublish(EXCHANGE_NAME, "email", null, message.getBytes());
            System.out.println("message send ok!");

        }
    }

}
