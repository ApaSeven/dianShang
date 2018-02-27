package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.KEYWORDS_T_MALL_SKU;
import com.atguigu.util.MyHttpGetUtil;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.MyPropertiesUtil;

@Controller
public class SearchContorller {

	@RequestMapping("search.do")
	public String index(String keywords,ModelMap map) {
		List<KEYWORDS_T_MALL_SKU> list_sku = new ArrayList<KEYWORDS_T_MALL_SKU>();
		
		//远程调用关键字检索功能
		try {
			String doGet = MyHttpGetUtil.doGet("http://localhost:8383/mall_0906_keywords_teacher/search/"+keywords+".do");
			//String doGet = MyHttpGetUtil.doGet(MyPropertiesUtil.getMyProperty("search.properties", "search_path"));
			list_sku= MyJsonUtil.json_to_list(doGet,KEYWORDS_T_MALL_SKU.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("list_sku", list_sku);
		map.put("keywords", keywords);

		return "sale_search";
	}
}
