package br.com.spedroza.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class ProducerWQ {

	private final static String QUEUE_NAME = "test.incoming";

	// main
	public static void main(String[] argv) throws Exception {

		// create a connection to the server
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.99.100");
		
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
		
			// declare queue
			System.out.println("Queue declare...");
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			//send some messages
			System.out.println("Sending messages...");
			
			// send 10 messages
			for(int i = 0; i < 10; i++) {
				String message = "Message no"+i;
				message = addDots(message, i);
				
				//publish the message
				channel.basicPublish("", QUEUE_NAME, MessageProperties.TEXT_PLAIN, message.getBytes());
				System.out.println("ProducerWQ - Message Sent " + message);	
			}

		} catch (Exception e) {
			System.out.println("ProducerWQ exception - " + e.getMessage());
		}
	}
	
	// add a number of dots at the end
	public static String addDots(String s, int n) {
		for (int i = 0; i < n; i++) {
			s = s + ".";
		}
	     return s;  
	}
}
