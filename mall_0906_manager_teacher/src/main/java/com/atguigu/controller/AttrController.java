package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bean.MOEL_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.service.AttrServiceInf;

@Controller
public class AttrController {
	
	@Autowired
	AttrServiceInf attrServiceInf;
	
	@RequestMapping("goto_attr")
	public String togo_attr() {
		
		return "manag_attr";
	}
	
	@RequestMapping("goto_attr_add")
	public String goto_attr_add() {
		
		return "manag_attr_add";
	}
	
	
	@RequestMapping("save_attr")
	//属性名,和属性值 
	public String save_attr(int flbh2,MOEL_T_MALL_ATTR list_attr) {
		
		//属性添加业务
		attrServiceInf.save_attr(flbh2,list_attr.getList_attr());
		
		
		return "redirect:/goto_attr_add.do";//重定向加中文参数乱码,medoAndView
	}
	
//根据二级目录查询,属性
	
	@RequestMapping("goto_attr_list")
	public String goto_attr_list() {
		
		return "manage_attr_list";
	}
//静态页
	@RequestMapping("get_attr_list")
	public String get_attr_list(int class_2_id,ModelMap map) {
		List<OBJECT_T_MALL_ATTR> list_attr = attrServiceInf.get_attr_by_class_2_id(class_2_id);
		map.put("list_attr", list_attr);
		
		return "manage_attr_list_inner";
	}
	
	
	
//返回json
	@ResponseBody
	@RequestMapping("get_attr_list_json")
	public List<OBJECT_T_MALL_ATTR> get_attr_list_json(int class_2_id,ModelMap map) {
		List<OBJECT_T_MALL_ATTR> list_attr = attrServiceInf.get_attr_by_class_2_id(class_2_id);
		map.put("list_attr", list_attr);
		
		return list_attr;
	}
	
	
}
