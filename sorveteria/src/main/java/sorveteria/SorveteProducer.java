package sorveteria;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class SorveteProducer {

    // Nome da fila
    private final static String QUEUE_NAME = "sorvete_fila";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://mmyuyypl:OIkTdCFjET0ui3wmgYy9zRIrEyM1nDyT@prawn.rmq.cloudamqp.com/mmyuyypl");  // Substitua pelo URI da sua instância no CloudAMQP

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String[] sabores = { "chocolate", "baunilha", "morango", "flocos" };

            for (String sabor : sabores) {
                String message = "Pedido de sorvete de " + sabor;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Pedido enviado: '" + message + "'");
            }
        }
    }
}
