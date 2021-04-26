package sml.directOrRouting;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Recv {
    private static String EXCHAGE_NAME = "javaForDirectExchange";

    private static Runnable runnable = ()->{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //创建交换机，一样，如果先启动消费端，没有该交换机会绑定报错
            channel.exchangeDeclare(EXCHAGE_NAME, "direct");
            String routingKey = Thread.currentThread().getName();
            //生成随机队列并绑定
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHAGE_NAME, routingKey);

            System.out.println("recived message...");
            channel.basicConsume(queueName,true,(consumerTag, delivery) -> {
                System.out.println("routingKey:" + routingKey + ",reviced message=>" + new String(delivery.getBody()));
            },consumerTag->{});

        }catch (Exception e){}
    };

    public static void main(String[] args) {
        new Thread(runnable,"wx").start();
        new Thread(runnable,"email").start();
    }

}
