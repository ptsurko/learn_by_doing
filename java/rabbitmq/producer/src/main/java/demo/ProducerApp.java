package demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class ProducerApp {
    public static void main(String[] args) throws URISyntaxException, KeyManagementException, NoSuchAlgorithmException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(new URI(System.getenv("AMQP_URI"))); //amqp://userName:password@hostName:portNumber/virtualHost"));
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();


//        var cf = new RabbitMQ.Client.ConnectionFactory
//        {
//        };
//
//        conn = cf.CreateConnection(); // one per application
//[...]
//        IModel chan = conn.CreateModel();
        channel.close();
        conn.close();


        System.out.println("Hello from producer");
    }
}