package pubsubsorveteria;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class SorveteriaPublisher {
    
    private static final String EXCHANGE_NAME = "sorvete_exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

            String message = "Novo sabor de pistache disponível!";

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("Sorveteria anunciou: '" + message + "'");
        }
    }
}
