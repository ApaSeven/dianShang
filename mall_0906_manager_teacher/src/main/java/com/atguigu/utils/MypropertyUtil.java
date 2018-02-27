package com.atguigu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MypropertyUtil {
	
	//读取配置文件的工具
	public static String getMyProperty(String property,String key) {
		
		Properties properties = new Properties();
		//读取此路径下的资源
		InputStream resourceAsStream = MypropertyUtil.class.getClassLoader().getResourceAsStream(property);
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String path = properties.getProperty(key);
		return path;
	}
}
