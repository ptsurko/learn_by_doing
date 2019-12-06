package demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class App {
    private final static String QUEUE = "events";

    private static void startProducer() {
        System.out.println("Producer: started");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setConnectionTimeout(30000);
        factory.setHost(System.getenv("AMQP_HOST"));
        factory.setUsername(System.getenv("AMQP_USER"));
        factory.setPassword(System.getenv("AMQP_PASS"));
        try {
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            System.out.println("Producer: connected to RabbitMQ");

            String message = "Hello";

            channel.basicPublish("", QUEUE, null, message.getBytes());
            channel.basicPublish("", QUEUE, null, message.getBytes());
            channel.basicPublish("", QUEUE, null, message.getBytes());

            System.out.println(String.format("Producer: sent message: %s", message));

            channel.close();
            conn.close();

            Thread.sleep(3000);
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Producer: shutdown");
    }

    private static void startConsumer(int consumerIndex) {
        System.out.println("Consumer" + consumerIndex + ": started");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setConnectionTimeout(30000);
        factory.setHost(System.getenv("AMQP_HOST"));
        factory.setUsername(System.getenv("AMQP_USER"));
        factory.setPassword(System.getenv("AMQP_PASS"));
        try {
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            System.out.println("Consumer" + consumerIndex + ": connected to RabbitMQ");

            AMQP.Queue.DeclareOk ok = channel.queueDeclare(QUEUE, false, false, false, null);

            System.out.println(String.format("Consumer" + consumerIndex + ": consumer count: %s, message count: %s", ok.getConsumerCount(), ok.getMessageCount()));

            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());

                System.out.println("Consumer" + consumerIndex + ": received message " + message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                } finally {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(QUEUE, false, deliverCallback, X -> {});

//            channel.close();
//            conn.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("Consumer" + consumerIndex + ": shutdown");
    }

    public static void main(String[] args) {
        Thread producer = new Thread(() -> startProducer());
        Thread consumer1 = new Thread(() -> startConsumer(1));
        Thread consumer2 = new Thread(() -> startConsumer(2));
        producer.start();
        consumer1.start();
        consumer2.start();
    }
}
