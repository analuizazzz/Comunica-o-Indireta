package sorveteria;

import com.rabbitmq.client.*;

public class SorveteConsumer {

    // Nome da fila
    private final static String QUEUE_NAME = "sorvete_fila";

    public static void main(String[] argv) throws Exception {
        // Configurar a conexão com o CloudAMQP
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://mmyuyypl:OIkTdCFjET0ui3wmgYy9zRIrEyM1nDyT@prawn.rmq.cloudamqp.com/mmyuyypl");  // Substitua pelo URI da sua instância no CloudAMQP

        // Criar conexão e canal
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declarar a fila (deve ter o mesmo nome do lado do produtor)
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Aguardando pedidos de sorvete...");

        // Callback para processar os pedidos de sorvete
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Pedido recebido: '" + message + "'");
            processarPedido(message);
        };

        // Consumir mensagens da fila
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

    private static void processarPedido(String pedido) {
        System.out.println(" [x] Processando: " + pedido);
        try {
            Thread.sleep(2000); // Simulando tempo de preparo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" [x] Pedido pronto: " + pedido);
    }
}