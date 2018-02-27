package com.atguigu.ws;

public class Test01 {

	public static void main(String[] args) {
		TestWsImpService wsImpService = new TestWsImpService();
		
		TestWsImp port = wsImpService.getTestWsImpPort();
		
		String ping = port.ping("lucy");
		
		System.out.println(ping);
		
	}

}
