package com.atguigu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.service.AttrServiceInf;
import com.atguigu.service.SkuListServiceInf;
import com.atguigu.utils.MyCacheUtil;

@Controller
public class CacheControll {
	
	@Autowired
	AttrServiceInf 	attrServiceInf;
	
	@Autowired
	SkuListServiceInf skuListServiceInf;
	
	@RequestMapping("goto_cache")
	public String goto_cache() {
		
		
		return "manage_cache";
	}
	
	//把中的属性对应的sku刷新到对应的key中
	
	//属性分辨率,对应三个型号,生成3个key ,每个key下对应多个sku
	@RequestMapping("refresh_attr_cache")
	@ResponseBody
	public long refresh_attr_cache(int class_2_id,@RequestParam("attrs[]")int[] attrs,ModelMap map) {
		long count = 01;
		
		//循环属性数组,将每个属性对应的属性值集合查询出来
		for (int i = 0; i < attrs.length; i++) {
			int attr_id = attrs[i];
			List<Integer>list_value_id = attrServiceInf.get_list_value_id_by_attr_id(attr_id);
			//生成key
			for (int j = 0; j < list_value_id.size(); j++) {
				Integer value_id = list_value_id.get(j);
				String key = "attr_"+class_2_id+"_"+attr_id+"_"+value_id;
				
				//根据属性检索商品集合
				List<T_MALL_SKU_ATTR_VALUE> list_av = new ArrayList<T_MALL_SKU_ATTR_VALUE>();
				T_MALL_SKU_ATTR_VALUE t_MALL_SKU_ATTR_VALUE = new T_MALL_SKU_ATTR_VALUE();
				t_MALL_SKU_ATTR_VALUE.setShxm_id(attr_id);
				t_MALL_SKU_ATTR_VALUE.setShxzh_id(value_id);
				list_av.add(t_MALL_SKU_ATTR_VALUE);
				List<OBJECT_T_MALL_SKU> list_sku = skuListServiceInf.get_sku_list_by_attr(class_2_id, list_av);
				
				//将key和对应的商品集合刷新入缓存
				MyCacheUtil.setMyListByKey(list_sku, key);
				count = count +list_sku.size();
			}
		
		}
		return count;
	}
}
