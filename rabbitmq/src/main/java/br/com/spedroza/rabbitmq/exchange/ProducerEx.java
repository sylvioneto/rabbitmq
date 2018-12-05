package br.com.spedroza.rabbitmq.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerEx {

	private final static String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {

		// create a connection to the server
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.99.100");
		
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
		
			// exchange declare
			System.out.println("Exchange declare...");
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			
			// send 10 messages to the exchange
		    System.out.println("Sending text messages to the exchange...");
			for(int i = 0; i < 10; i++) {
				String message = "Hello World! Count "+i;
				channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
				System.out.println("Message Sent " + message);	
			}

		} catch (Exception e) {
			System.out.println("ProducerQ exception: " + e.getMessage());
		}
	}
}
