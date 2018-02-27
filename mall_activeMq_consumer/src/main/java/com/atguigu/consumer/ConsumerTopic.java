package com.atguigu.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerTopic {
	
	//消费消息端服务：
	public static void main(String[] args) throws Exception{
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("producer-Topic");//接收这个消息从mq中
		MessageConsumer consumer = session.createConsumer(topic);
		// 接收消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				// 打印结果
				TextMessage textMessage = (TextMessage) message;
				String text = null;
				try {
					text = textMessage.getText();
				} catch (JMSException e) {
					e.printStackTrace();
				}
				System.out.println(text+"我是盗贼");
			}
		});
		//System.out.println();
		// 等待接收消息
		System.in.read();
	}


}


