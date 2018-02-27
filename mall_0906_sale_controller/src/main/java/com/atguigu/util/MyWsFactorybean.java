package com.atguigu.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.FactoryBean;

import com.atguigu.server.UserServer;
//利用这个工厂进行远程调用代理类的spring注入
public class MyWsFactorybean<T> implements FactoryBean<T>{
	
	
	private String url;
	private Class<T>t;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}
	
	public static <T>T getMyWs(String url,Class<T>t){
		JaxWsProxyFactoryBean wepftb = new JaxWsProxyFactoryBean();
		
		wepftb.setAddress(url);
		wepftb.setServiceClass(t);
		//加入安全拦截器
		if (t.getSimpleName().equals("TestWsInf")) {
			//加入安全拦截器
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			map.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
			map.put(WSHandlerConstants.PW_CALLBACK_CLASS, MyPasswordCallback.class.getName());
			map.put(WSHandlerConstants.USER, "username");
			WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(map);
			wepftb.getOutInterceptors().add(wss4jOutInterceptor);
		}
		
		T create = (T)wepftb.create();
		return create;
	}

	@Override
	public 	T getObject() throws Exception {
		// TODO Auto-generated method stub
		return getMyWs(this.url,this.t);
	}

	@Override
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
