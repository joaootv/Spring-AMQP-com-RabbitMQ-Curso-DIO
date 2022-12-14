package Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceverTopic {

    private static String NAME_EXCHANGE = "TopicExchange";

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

        //o servidor irá determinar um nome randomico para esta fila
        //consequentemente ela será temporaria
        String nameQueue = channel.queueDeclare().getQueue();
        String bindingKey = "#.rabbit";

        //declaracao da exchange
        channel.exchangeDeclare(NAME_EXCHANGE, "topic");
        channel.queueBind(nameQueue, NAME_EXCHANGE, bindingKey);

        DeliverCallback  deliverCallback = (ConsumeTag, delivery) ->{
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("[*] Received message: '" + message + "'");
        };

        channel.basicConsume(nameQueue, true, deliverCallback, ConsumerTag->{});
    }
}
