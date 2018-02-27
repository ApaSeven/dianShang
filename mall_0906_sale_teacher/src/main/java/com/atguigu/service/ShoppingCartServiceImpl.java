package com.atguigu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.mapper.ShoppingCartMapper;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartServiceInf{
	
	@Autowired
	ShoppingCartMapper shoppingCartMapper;

	@Override
	public List<T_MALL_SHOPPINGCAR> get_shoppingCart_by_user_id(int hy_id) {
		return shoppingCartMapper.select_shoppingCart_by_user_id(hy_id);
		
	}

	@Override
	public void add_shoppingCart(T_MALL_SHOPPINGCAR cart) {
		shoppingCartMapper.add_shoppingCart(cart);
		
	}

	@Override
	public void update_shoppingCart(T_MALL_SHOPPINGCAR cart) {
		shoppingCartMapper.update_shoppingCart(cart);
		
	}

}
