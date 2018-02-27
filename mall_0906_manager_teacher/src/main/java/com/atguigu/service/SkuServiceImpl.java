package com.atguigu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.mapper.SkuMapper;
@Service
public class SkuServiceImpl implements SkuServiceInf{
	
	@Autowired
	SkuMapper skuMapper;

	public void save_sku(T_MALL_SKU sku, List<T_MALL_SKU_ATTR_VALUE> list_av) {
		 //先插入sku返回主键
		skuMapper.insert_sku(sku);
		
		//插入中间表T_MALL_SKU_ATTR_VALUE
		Map<Object,Object>map = new HashMap<>();
		map.put("sku_id", sku.getId());
		map.put("list_av", list_av);
		map.put("shp_id", sku.getShp_id());
		
		skuMapper.insert_list_av(map);
		
	}

}
