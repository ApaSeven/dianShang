package com.atguigu.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU;

public interface SkuListMapper {

	List<OBJECT_T_MALL_SKU> select_sku_list_by_class2(int class_2_id);
	
	List<OBJECT_T_MALL_SKU> select_sku_list_by_attr(Map<Object,Object>map);

	DETAIL_T_MALL_SKU get_sku_detail(Map<Object, Object> map);

	List<T_MALL_SKU> get_sku_list_by_spu_id(int spu_id);

	

}
