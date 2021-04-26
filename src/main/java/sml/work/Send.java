package sml.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Send {

    private static String QUEUE_NAME = "javaForWorkQueue";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            //持久化，服务器重启队列还在。注意：队列不存在创建时才生效，已存在不生效
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            for (int i = 1; i <= 4; i++) {
                String message = "第" + i + "次消息";
                // 第三个参数为消息持久化
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("send ok");
            }

        }

    }
}
