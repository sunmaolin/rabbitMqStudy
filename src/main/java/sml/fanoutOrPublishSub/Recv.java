package sml.fanoutOrPublishSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Recv {
    private static String EXCHANGE_NAME = "javaForFanoutExchange";

    private static Runnable runnable = () -> {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            //创建一个随机名称的队列
            String queueName = channel.queueDeclare().getQueue();
            //交换机与队列进行绑定
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            channel.basicConsume(queueName, true, (consumerTag, delivery) -> {
                System.out.println("recived =>" + new String(delivery.getBody()));
            }, consumerTag -> {
            });
        }catch (Exception e){}

    };

    public static void main(String[] args) throws Exception {
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
