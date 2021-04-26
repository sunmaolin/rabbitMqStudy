package sml.directOrRouting;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    private static String EXCHAGE_NAME = "javaForDirectExchange";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            //创建交换机
            channel.exchangeDeclare(EXCHAGE_NAME, "direct");

            //发送消息到交换机
            String message = "hello wx";
            channel.basicPublish(EXCHAGE_NAME, "wx", null, message.getBytes());

            System.out.println("message send ok!");

        }
    }
}
