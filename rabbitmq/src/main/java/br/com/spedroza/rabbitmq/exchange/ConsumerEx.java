package br.com.spedroza.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ConsumerEx {

	private final static String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {

		// create a connection to the server
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("192.168.99.100");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

		// exchange declare
	    // FANOUT broadcasts all the messages it receives to all the queues it knows
	    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	    
	    // temp queue declare
	    String queueName = channel.queueDeclare().getQueue();

	    // bind the temp queue to the exchange
	    channel.queueBind(queueName, EXCHANGE_NAME, "");
	    System.out.println("Waiting for messages. To exit press CTRL+C");
		
	    //deliver call back
	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        String message = new String(delivery.getBody(), "UTF-8");
	        System.out.println(" ConsumerEx Received - " + message + " on "+queueName);
	    };
	    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
	}
}
