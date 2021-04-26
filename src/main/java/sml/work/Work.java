package sml.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Work {

    private static String QUEUE_NAME = "javaForWorkQueue";

    private static Runnable runnable = ()->{
        String currentWorkName = Thread.currentThread().getName();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // 一次取一个消息消费
            channel.basicQos(1);

            System.out.println("all worker start waiting message..");
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME,autoAck,(consumerTag, delivery)->{
                System.out.println(currentWorkName + " received:=>" + new String(delivery.getBody()));
                // autoAck为false、如果消费成功我们需要手动确认告诉服务端消费成功
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            },consumerTag->{});

        }catch (Exception e){
            e.printStackTrace();
        }
    };

    public static void main(String[] args) {
        new Thread(runnable,"work1").start();
        new Thread(runnable,"work2").start();
    }

}
