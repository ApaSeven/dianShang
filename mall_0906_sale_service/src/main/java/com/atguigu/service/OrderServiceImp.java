package com.atguigu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.exception.OverSaleException;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.util.MyDateUtil;

@Service
public class OrderServiceImp implements OrderService{
	
	@Autowired
	OrderMapper orderMapper;
	
	//保存订单的业务
	@Override
	public void save_order(OBJECT_T_MALL_ORDER order, T_MALL_ADDRESS address) {
		
		//保存订单表,生成主键
		Map<Object, Object> order_map = new HashMap<Object, Object>();
		order.setJdh(1);
		order_map.put("order", order);
		order_map.put("address", address);
		orderMapper.insert_order(order_map);
		
		//购物车
		List<Integer>list_cart_id = new ArrayList<Integer>();
		
		//保存物流包裹,生成主键
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			
			Map<Object, Object> flow_map = new HashMap<Object, Object>();
			flow_map.put("dd_id", order.getId());
			list_flow.get(i).setMdd(address.getYh_dz());
			flow_map.put("flow", list_flow.get(i));
			orderMapper.insert_flow(flow_map);
			
			//根据物流id保存订单详情
			List<T_MALL_ORDER_INFO> list_info = list_flow.get(i).getList_info();
			Map<Object, Object> info_map = new HashMap<Object, Object>();
			info_map.put("list_info", list_info);
			info_map.put("dd_id", order.getId());
			info_map.put("flow_id", list_flow.get(i).getId());
			orderMapper.insert_infos(info_map);
			
			for (int j = 0; j < list_info.size(); j++) {
				list_cart_id.add(list_info.get(j).getGwch_id());
			}
		}
		
		//根据保存的订单信息,删除购物车数据
		orderMapper.delete_shoppingCart(list_cart_id);
	}

	
	//支付完成后,需要用生成的订单,调用订单通知业务
	@Override
	public void pay_order(OBJECT_T_MALL_ORDER order) throws OverSaleException {
		
		List<OBJECT_T_MALL_FLOW> list_flow = order.getList_flow();
		for (int i = 0; i < list_flow.size(); i++) {
			OBJECT_T_MALL_FLOW flow = list_flow.get(i);
			//修改库存单元状态
			List<T_MALL_ORDER_INFO> list_info = flow.getList_info();
			for (int j = 0; j < list_info.size(); j++) {
				int sku_id = list_info.get(j).getSku_id();
				
				//查询剩余sku数量
				long kc = get_kc(sku_id);
				
				if (kc > list_info.get(j).getSku_shl()) {
					//更新sku库存,增加销量
					orderMapper.update_kc(list_info.get(j));
				}else {
					//sku数量不足,回滚订单操作,给提示--------抛个异常   
					throw new OverSaleException("over sale");
				}
			}
			//修改物流状态,生成物流信息
			//生成物流包裹的其他信息,配送时间,配送描述,业务员,联系方式
			flow.setLxfsh("13888888888");
			flow.setPsshj(MyDateUtil.getMyDate(1));//物流接口,配送时间
			flow.setPsmsh("商品正在出库");
			flow.setYwy("马云");
			
			orderMapper.update_flow(flow);
			
		}
		
		//修改订单状态(进度号),预计送达时间---订单状态更新为已支付
		order.setJdh(3);
		order.setYjsdshj(MyDateUtil.getMyDate(3));//物流接口
		orderMapper.update_order(order);
		
	}

	
	
	//库存查询的业务,加入查询锁,保证不会发生不可重复读的事务
	private long get_kc(int sku_id) {
		
		long kc = 01;
		
		int select_count = orderMapper.select_count(sku_id);
		
		if (select_count == 1) {
			kc = orderMapper.select_kc(sku_id);
		}else {
			kc = orderMapper.select_kc_for_update(sku_id);//库存不足,加锁
		}
		
		return kc;
	}

}
