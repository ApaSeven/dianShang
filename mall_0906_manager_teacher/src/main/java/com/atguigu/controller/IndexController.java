package com.atguigu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.atguigu.bean.T_MALL_PRODUCT;
import com.atguigu.service.SpuServiceInf;
import com.atguigu.utils.MyUploadUtil;

@Controller
public class IndexController {
	
	@Autowired
	SpuServiceInf spuServiceInf;
	
	
	@RequestMapping("index")
	public String index(String url,String title,ModelMap map) {
		map.put("url", url);
		map.put("title", title);
		return "manag_index";
	}
	
	@RequestMapping("goto_spu_add")
	public String goto_spu_add() {
		
		return "goto_spu_add";
	}
	
	
	@RequestMapping("save_spu")
	public ModelAndView save_spu(T_MALL_PRODUCT spu,@RequestParam("files") MultipartFile[] files) {
		
		//上传图片,返回图片名(工具类上传图片),返回图片名
		List<String> list_image = MyUploadUtil.upload_image(files);
		
		
		
		//保存spu,生成主键,批量保存图片信息
		spuServiceInf.save_spu(spu,list_image);
		
		ModelAndView mv = new ModelAndView("redirect:/index.do");
		mv.addObject("url","goto_spu_add.do");
		mv.addObject("title","商品信息添加");
		
		
		return mv;
	}
	
	
	
	
	
	
	
	
}
