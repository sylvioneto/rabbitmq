package br.com.spedroza.rabbitmq.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerQ {

	private final static String QUEUE_NAME = "test.incoming";

	public static void main(String[] argv) throws Exception {

		// create a connection to the server
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.99.100");
		
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
		
			// declare will create the queue in case it does not exists
			System.out.println("Queue declare...");
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			//send a hello world message
			System.out.println("Sending message...");
			
			// send 10 messages
			for(int i = 0; i <= 10; i++) {
				String message = "Hello World! Count "+i;
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println("Message Sent " + message);	
			}

		} catch (Exception e) {
			System.out.println("ProducerQ exception: " + e.getMessage());
		}
	}
}
