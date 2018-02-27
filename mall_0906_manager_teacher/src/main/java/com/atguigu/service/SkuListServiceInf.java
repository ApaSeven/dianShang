package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;

public interface SkuListServiceInf {

	List<OBJECT_T_MALL_SKU> get_sku_list_by_class2(int class_2_id);

	List<OBJECT_T_MALL_SKU> get_sku_list_by_attr(int class_2_id, List<T_MALL_SKU_ATTR_VALUE> list_av);
	
//商品详情
	DETAIL_T_MALL_SKU get_sku_detail(int sku_id, int spu_id);

	List<T_MALL_SKU> get_sku_list_by_spu_id(int spu_id);


}
