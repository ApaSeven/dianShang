package com.atguigu.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.service.AttrServiceInf;
import com.atguigu.service.SkuListServiceInf;
import com.atguigu.util.JedisPoolUtils;
import com.atguigu.util.MyCacheUtil;
import com.atguigu.util.MyJsonUtil;

import redis.clients.jedis.Jedis;

@Controller
public class SkuListController {
	
	@Autowired
	AttrServiceInf attrServiceInf;
	
	@Autowired
	SkuListServiceInf skuListServiceInf;
	
	//根据二级目录查询sku
	@RequestMapping("goto_sku_list")
	public String goto_sku_list(int class_2_id,ModelMap map) {
		
		List<OBJECT_T_MALL_SKU> list_sku = new ArrayList<OBJECT_T_MALL_SKU>();
		
		//调用商品sku内容检索的业务
		
		//在redis中查询,如果查不到就去mysql
		String key = "class_2_"+class_2_id;
		list_sku = MyCacheUtil.getMyListByKey(key,OBJECT_T_MALL_SKU.class);
		
		
		//去mysql中查询
		if (list_sku==null||list_sku.size()==0) {
			list_sku = skuListServiceInf.get_sku_list_by_class2(class_2_id);
		
			//同步缓存         (应该是并行操作,消息队列)
			MyCacheUtil.setMyListByKey(list_sku, key);
			
		}
		
		
		
		List<OBJECT_T_MALL_ATTR> list_attr = attrServiceInf.get_attr_by_class_2_id(class_2_id);
		map.put("list_attr", list_attr);//属性
		map.put("list_sku", list_sku);//大sku
		map.put("class_2_id", class_2_id);
		
		return"sale_sku_list";
	}
	
	
	
	
	
	//根据多个属性联合查询出sku	,交叉检索							//用数组传@RequestParam("attr_param[]")String[] attr_param			//MODEL_T_MALL_SKU_ATTR_VALUE list_av
	@RequestMapping("get_sku_list_by_attr")                         
	public String get_sku_list_by_attr(int class_2_id,MODEL_T_MALL_SKU_ATTR_VALUE list_av,ModelMap map,String order) {
		
		List<OBJECT_T_MALL_SKU> list_sku = new ArrayList<OBJECT_T_MALL_SKU>();
		
		List<T_MALL_SKU_ATTR_VALUE> list_sku_av = list_av.getList_stu_av();
		
		String new_key = null;
		
		if (list_sku_av!=null&&list_sku_av.size()>0) {
			
			String[] keys = new String[list_sku_av.size()];//选了几个属性条件
			//获取各个key的名字
			for (int i = 0; i < list_sku_av.size(); i++) {
				String key = "attr_"+class_2_id+"_"+ list_sku_av.get(i).getShxm_id()+"_"+list_sku_av.get(i).getShxzh_id();
				keys[i] = key;
			}
			//调用属性检索的业务,redis
			new_key = MyCacheUtil.interKey(keys);//新集合的redis中的key
			
			list_sku = MyCacheUtil.getMyListByKey(new_key,OBJECT_T_MALL_SKU.class);//根据新的key查出交叉车的value
			
		}
	
		
		//redis中没有从mysql中查
		 
		//mysql(有属性名称id,和属性值id,二级分类id)
		if (list_sku == null||list_sku.size()==0) {
			list_sku = skuListServiceInf.get_sku_list_by_attr(class_2_id, list_av.getList_stu_av(),order);
		
			//同步缓存,消息队列的异步同步
			MyCacheUtil.setMyListByKey(list_sku, new_key);
		}
		
		map.put("list_sku", list_sku);
		return "sale_sku_list_inner";
	}
	
	
	
	//获取商品详情
	@RequestMapping("goto_sku_detail")  //spu_id 查下边sku的列表   sku_id  改商品的详细信息,包括该商品关联的有用的信息 
	public String goto_sku_detail(int sku_id,int spu_id,ModelMap map) {
		//调用查询商品详情的业务
		//1.查询商品详情
		DETAIL_T_MALL_SKU obj_sku = skuListServiceInf.get_sku_detail(sku_id,spu_id);
		
		//2.根据spu_id查寻下边的sku_list
		List<T_MALL_SKU> list_sku = skuListServiceInf.get_sku_list_by_spu_id(spu_id);
		
		map.put("obj_sku", obj_sku);
		map.put("list_sku", list_sku);
		
		return "sale_search_detail";
	}
	
	
	//排序,根据不同要求
	
	
	
}
