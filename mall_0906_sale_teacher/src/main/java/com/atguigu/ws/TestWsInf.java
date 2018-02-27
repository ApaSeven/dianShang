package com.atguigu.ws;

import javax.jws.WebService;

@WebService
public interface TestWsInf {
	public String ping(String say);
}
