package PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceverPubSub {

    private  static String NAME_QUEUE = "broadcast";
    private static String NAME_EXCHANGE = "fanoutExchange";

    public static void main(String[] args0) throws Exception {
        //primeiro criar a conexão
        //setar as informações para cria-la
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.20.0.2");
        factory.setUsername("admin");
        factory.setPassword("passw123");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        //System.out.println(connection.hashCode());

        // criar um novo canal
        Channel channel = connection.createChannel();
        System.out.println(channel);

        // declarar a fila que será utilizada
        // nome da fila, exclusiva, autodelete, durable, map(args)
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        //declaracao da exchange
        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");
        channel.queueBind(NAME_QUEUE, NAME_EXCHANGE, "");

        DeliverCallback  deliverCallback = (ConsumeTag, delivery) ->{
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("[*] Received message: '" + message + "'");
        };

        channel.basicConsume(NAME_QUEUE, true, deliverCallback, ConsumerTag->{});
    }
}