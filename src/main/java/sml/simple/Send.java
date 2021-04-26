package sml.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    private final static String QUEUE_NAME = "javaForQueue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setVirtualHost("/");

        // try后面括号中如果实现了autoClose接口会自动释放
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello Java for Queue";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("message:"+message+"=>send OK!");
        }
    }
}
