package com.atguigu.test;

import javax.xml.ws.Endpoint;

import com.atguigu.ws.TestWsImp;
import com.atguigu.ws.TestWsInf;

public class TestWs {

	public static void main(String[] args) {
		TestWsInf ws = new TestWsImp();
		
		Endpoint.publish("http://192.168.13.34:5555/ws", ws);
		
	}

}
