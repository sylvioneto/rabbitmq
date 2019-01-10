package br.com.spedroza.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ConsumerWQ {

	private final static String QUEUE_NAME = "test.incoming";

	public static void main(String[] argv) throws Exception {

		// create a connection to the server
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("192.168.99.100");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

		// queues declare
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    System.out.println("Waiting for messages. To exit press CTRL+C");
		
	    //deliver call back
	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        String message = new String(delivery.getBody(), "UTF-8");
	        System.out.println("Consumer WQ Received - " + message);
	        
	        try {
				doWork(message);
			} catch (Exception e) {
				System.out.println("Exception - "+e.getMessage());
				e.printStackTrace();
			}
	    };
	    
	    boolean autoACK = false;
	    channel.basicConsume(QUEUE_NAME, autoACK, deliverCallback, consumerTag -> { });
	}
	
	// execute any action just to simulate some process
	private static void doWork(String task) throws InterruptedException {
	    
		if(task.contains("5")) {
			throw new RuntimeException("Testing NACK");
		}
		
		for (char ch: task.toCharArray()) {
	        if (ch == '.') Thread.sleep(1000);
	    }
	}
}
