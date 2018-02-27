package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("index")
	public String index(@CookieValue(value="yh_nch",required=false) String yh_nch , HttpServletRequest request,ModelMap map) {

		
		
		//取出cookie中的用户昵称,return回页面
	
		/*Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			boolean b = cookies[i].getName().equals("yh_nch");
			if (b) {
				yh_nch = cookies[i].getValue();
			}
		}*/
		
		/*
		try {
			map.put("yh_nch", URLDecoder.decode(yh_nch, "urf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return "sale_index";
	}

}
