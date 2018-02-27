package com.atguigu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.atguigu.bean.OBJECT_T_MALL_FLOW;
import com.atguigu.bean.OBJECT_T_MALL_ORDER;
import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_ORDER_INFO;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.exception.OverSaleException;
import com.atguigu.server.AddressServer;
import com.atguigu.service.OrderService;
import com.atguigu.service.ShoppingCartServiceInf;

@Controller
@SessionAttributes("order")//向session域放一个对象,名为order,
							//下边map向request域中放了一个order,这里自动把他转存到session域中
							//在这个controller范围内有效
public class OrderController {
	
	@Autowired
	AddressServer addressServer;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ShoppingCartServiceInf shoppingCartServiceInf;
	
	//拆单
	@RequestMapping("check_order")
	public String check_order(HttpSession session,ModelMap map) {
		List<T_MALL_SHOPPINGCAR>list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		OBJECT_T_MALL_ORDER order = new OBJECT_T_MALL_ORDER();
		
		
		if (user==null) {
			//没登录让去登录
			return "redirect:/goto_login.do";
		}else {
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
			
			// sum,yh_id,进度
			order.setZje(get_sum(list_cart));
			order.setYh_id(user.getId());
			order.setJdh(0);
			
			//拆单--根据库存
			//根据购物车商品的库存地址拆单,开始拆单
			Set<String> set_kcdz = new HashSet<>();
			for (int i = 0; i < list_cart.size(); i++) {
				//根据购物车商品的库存地址确定拆单的个数
				//地址去重,看要分成几个包裹
				set_kcdz.add(list_cart.get(i).getKcdz());//不重复的库存地址
			}
			//循环库存地址,将订单中的商品数据按照库存地址放入库存包裹 ,商品-->包裹
			List<OBJECT_T_MALL_FLOW>list_flow = new ArrayList<OBJECT_T_MALL_FLOW>();
			
				Iterator<String> iterator = set_kcdz.iterator();
				while (iterator.hasNext()) {
					String kcdz = iterator.next();
					
					//配送方式,目前地点,用户id
					OBJECT_T_MALL_FLOW flow = new OBJECT_T_MALL_FLOW();//每一个地址,对应一个包裹
					flow.setPsfsh("顺丰快递");
					flow.setMqdd("商品未出库");
					flow.setYh_id(user.getId());
					
					//循环购物车
					List<T_MALL_ORDER_INFO>list_info = new ArrayList<T_MALL_ORDER_INFO>();
					for (int i = 0; i < list_cart.size(); i++) {
						
						//info订单详细信息,放的是商品信息
						if (list_cart.get(i).getShfxz().equals("1")&&list_cart.get(i).getKcdz().equals(kcdz)) {
							T_MALL_ORDER_INFO info = new T_MALL_ORDER_INFO();
							info.setGwch_id(list_cart.get(i).getId());
							info.setShp_tp(list_cart.get(i).getShp_tp());
							info.setSku_id(list_cart.get(i).getSku_id());
							info.setSku_jg(list_cart.get(i).getSku_jg());
							info.setSku_kcdz(kcdz);
							info.setSku_mch(list_cart.get(i).getSku_mch());
							info.setSku_shl(list_cart.get(i).getTjshl());
							//购物车中的商品信息放入订单信息
							list_info.add(info);
						}
					}
					flow.setList_info(list_info);
					//把包裹放入集合中
					list_flow.add(flow);
				}
			
			
			//将库存包裹放入订单对象中  包裹集合--->订单
				order.setList_flow(list_flow);
		
		}
		
		System.out.println(order);
		map.put("order", order);
		map.put("list_address", addressServer.get_addresses_by_user_id(user));
		return "sale_chect_order";
	}
	
	//计算购物车总金额的方法
		private BigDecimal get_sum(List<T_MALL_SHOPPINGCAR> list_cart) {
			BigDecimal sum = new BigDecimal("0");
			for (int i = 0; i < list_cart.size(); i++) {
				if (list_cart.get(i).getShfxz().equals("1")) {
					//shfxz=1,是被选中的
					sum = sum.add(new BigDecimal(list_cart.get(i).getHj()+""));
				}
			}
			
			return sum;
		}
		
		//保存订单
		
		//取上边@sessionAttribute("order")
		@RequestMapping("save_order")
		public String sava_order(@ModelAttribute("order") OBJECT_T_MALL_ORDER order,HttpSession session,int address_id) {
			T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");

			//保存订单的业务----提交订单
			T_MALL_ADDRESS address = addressServer.get_addresses_by_id(address_id);
			orderService.save_order(order,address);
			
			//重新同步购物车的session
			session.setAttribute("list_cart_session", shoppingCartServiceInf.get_shoppingCart_by_user_id(user.getId()));
			return "redirect:/goto_pay.do";
		}
		
		
		
		
		@RequestMapping("goto_pay")
		public String goto_pay() {
			
			return "sale_pay";
		}
		
		
		@RequestMapping("pay_after")
		public String pay_after(@ModelAttribute("order") OBJECT_T_MALL_ORDER order) {
			
			//支付成功后,调用订单通知业务
			try {
				orderService.pay_order(order);
			} catch (OverSaleException e) {
				
				e.printStackTrace();
				return "sale_fail";//商品不够了,自己抛的异常
			}
			
			return "sale_success";
		}
		
		
}
