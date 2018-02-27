package com.atguigu.producer;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class producerTopic {

	//订阅模式producer
	public static void main(String[] args) throws Exception{
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("producer-Topic");//订阅模式下发布消息
		MessageProducer producer = session.createProducer(topic);
		
		
		TextMessage textMessage = session.createTextMessage("为了部落!!!");
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();


	}

}
