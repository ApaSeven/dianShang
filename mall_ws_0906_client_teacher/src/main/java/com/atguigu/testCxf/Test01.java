package com.atguigu.testCxf;

import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.server.UserServer;

public class Test01 {

	public static void main(String[] args) {
		
		JaxWsProxyFactoryBean wepftb = new JaxWsProxyFactoryBean();
		
		wepftb.setAddress("http://localhost:8080/mall_0906_user_teacher/user?wsdl");
		wepftb.setServiceClass(UserServer.class);
		
		UserServer create = (UserServer)wepftb.create();
		
		T_MALL_USER_ACCOUNT user = new T_MALL_USER_ACCOUNT();
		user.setYh_mch("lilei");
		user.setYh_mm("123");
		T_MALL_USER_ACCOUNT login = create.login(user);
		
		System.out.println(login);
	}

}
