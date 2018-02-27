package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;

public interface ShoppingCartServiceInf {

	List<T_MALL_SHOPPINGCAR> get_shoppingCart_by_user_id(int id);

	void add_shoppingCart(T_MALL_SHOPPINGCAR cart);

	void update_shoppingCart(T_MALL_SHOPPINGCAR cart);
		
	
	

	

}
