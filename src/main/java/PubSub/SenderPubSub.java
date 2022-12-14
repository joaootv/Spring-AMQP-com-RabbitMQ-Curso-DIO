package PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderPubSub {

    private static String NAME_EXCHSAGE = "fanoutExchange";

    public static void main(String[] args0) throws Exception{
        //primeiro criar a conexão
        //setar as informações para cria-la
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.20.0.2");
        factory.setUsername("admin");
        factory.setPassword("passw123");
        factory.setPort(5672);

        try(Connection connection = factory.newConnection()) {
            //System.out.println(connection.hashCode());

            // criar um novo canal
            Channel channel = connection.createChannel();
            System.out.println(channel);

            // declarar a fila que será utilizada
            // nome da fila, exclusiva, autodelete, durable, map(args)
            channel.exchangeDeclare(NAME_EXCHSAGE, "fanout");

            //criar a mensagem
            String message = "Hello! This is a pub/sub system!";

            //enviar a mensagem
            channel.basicPublish(NAME_EXCHSAGE, "", null, message.getBytes());

            System.out.println("[x] Sent '" + message + "'");
        }
    }
}
