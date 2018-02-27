package com.atguigu.ws;

import javax.jws.WebService;

@WebService//这个就可以让这个借口发布出去给别人使用              放在实现类上
public class TestWsImp implements TestWsInf{

	@Override
	public String ping(String say) {
		System.err.println("服务器调用:"+say);
		return "pong";
	}

}
