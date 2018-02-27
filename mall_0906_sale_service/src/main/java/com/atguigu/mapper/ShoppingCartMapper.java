package com.atguigu.mapper;

import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;

public interface ShoppingCartMapper {

	 List<T_MALL_SHOPPINGCAR> select_shoppingCart_by_user_id(int yh_id) ;
	

	void update_shoppingCart(T_MALL_SHOPPINGCAR cart) ;
	

	 void add_shoppingCart(T_MALL_SHOPPINGCAR cart) ;
	
}
