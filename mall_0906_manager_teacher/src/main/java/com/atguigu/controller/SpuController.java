package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bean.T_MALL_PRODUCT;
import com.atguigu.service.SpuServiceInf;

@Controller
public class SpuController {
	
	@Autowired
	SpuServiceInf spuServiceInf;
	
	
	@RequestMapping("get_spu_list")
	@ResponseBody
	public List<T_MALL_PRODUCT> get_spu_list(int class_2_id,int pp_id){
		
		List<T_MALL_PRODUCT> list_spu = spuServiceInf.get_spu_list(class_2_id,pp_id);
		
		return list_spu;
	}

}
