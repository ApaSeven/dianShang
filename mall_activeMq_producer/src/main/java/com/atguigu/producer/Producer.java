package com.atguigu.producer;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	public static void main(String[] args) {
		
		//服务器地址
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		

		try {
			//发送消息到mq
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("queue1");
			MessageProducer producer = session.createProducer(queue);	
			TextMessage textMessage = session.createTextMessage("来杯水");
			producer.send(textMessage);
			
			producer.close();
			session.close();
			connection.close();
			
		} catch (Exception e) {
			
		}
		


	}

}
