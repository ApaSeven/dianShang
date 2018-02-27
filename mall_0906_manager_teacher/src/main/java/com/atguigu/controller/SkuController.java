package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.T_MALL_PRODUCT;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.service.AttrServiceInf;
import com.atguigu.service.SpuServiceInf;
import com.atguigu.service.SkuServiceInf;
import com.atguigu.utils.MyUploadUtil;

@Controller
public class SkuController {
	
	@Autowired
	AttrServiceInf attrServiceInf;
	
	@Autowired
	SkuServiceInf skuServiceInf;
	
	
	
	@RequestMapping("goto_sku")
	public String goto_sku() {
		return "manage_sku";
	}
	
	@RequestMapping("goto_sku_add")
	public String goto_sku_add() {
		return "manage_sku_add";
	}
	
	//添加库存商品单元     向T_MALL_SKU.java  T_MALL_SKU_ATTR_VALUE.java插入数据
	
	//属性id 属性值id 
	@RequestMapping("save_sku")
	public String save_sku(T_MALL_SKU sku,MODEL_T_MALL_SKU_ATTR_VALUE list_stu_av) {
		
		skuServiceInf.save_sku(sku,list_stu_av.getList_stu_av());
		
		return "redirect:/goto_sku_add.do";
		
	}
	
	
	//根据二级分类,查出其相应的属性
	@RequestMapping("sku_get_attr")
	public String sku_get_attr(int class_2_id , ModelMap map) {
		
		List<OBJECT_T_MALL_ATTR> list_attr = attrServiceInf.get_attr_by_class_2_id(class_2_id);
		
		map.put("list_attr", list_attr);
		
		System.out.println("888");
		return "manage_sku_attr_list_inner";
	}
	
	
	
	
}
