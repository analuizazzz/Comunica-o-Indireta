package pubsubsorveteria;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class SorveteriaSubscriber {
    private final static String QUEUE_NAME = "cliente_queue_1"; 
    private static final String EXCHANGE_NAME = "sorvete_exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println(" [*] Aguardando mensagens. Para sair, pressione CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recebido '" + message + "'");
        };
        
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

        while (true) {
            Thread.sleep(1000); // espera 1 segundo
        }

    }
}
