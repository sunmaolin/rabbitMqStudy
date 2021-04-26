package sml.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Recv {

    private static String EXCHANGE_NAME = "javaForTopicExchange";

    private static Runnable runnable = ()->{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String routingKey = Thread.currentThread().getName();
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

            System.out.println("reciving message...");
            channel.basicConsume(queueName,true,(consumerTag,delivery)->{
                System.out.println("routingKey:" + routingKey + ",recived=>" + new String(delivery.getBody()));
            },consumerTag->{});

        }catch (Exception e){
            e.printStackTrace();
        }
    };

    public static void main(String[] args) {
        new Thread(runnable,"email.#").start();
        new Thread(runnable,"email.*").start();
    }
}
