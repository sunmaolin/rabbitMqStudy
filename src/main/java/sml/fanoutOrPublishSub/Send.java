package sml.fanoutOrPublishSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    public static String EXCHANGE_NAME = "javaForFanoutExchange";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //创建一个交换机exchange
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "Hello JavaForExchange";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("message send ok!");

        }
    }

}
