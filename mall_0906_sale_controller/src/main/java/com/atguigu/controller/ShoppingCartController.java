package com.atguigu.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.service.ShoppingCartServiceInf;
import com.atguigu.util.MyJsonUtil;

@Controller
public class ShoppingCartController {
	
	@Autowired
	ShoppingCartServiceInf shoppingCartServiceInf;
	
	//添加购物车,未登录的情况
	/*@RequestMapping("add_cart")
	public String add_cart(@CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie,
			HttpServletResponse response,T_MALL_SHOPPINGCAR cart,HttpSession session,HttpServletRequest request, ModelMap map) {
		//调用购物车添加业务
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT)session.getAttribute("user");
		
		//未登录,操作cookie              未登录添加到cookie中,登录后添加到db,然后同步到session
		if (user==null) {
			
			if (list_cart_cookie==null||list_cart_cookie.equals("")) {
				//cookie为空,添加新数据
				list_cart.add(cart);
			}else {
				//cookie中有值
				//list_cart_cookie转化为购物车对象集合(list_cart_cookie)是json字符串
				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				boolean b = if_new_cart(list_cart,cart);
				
				if (b) {
					//这条数据,cookie中没有
					list_cart.add(cart);//加到cookie中
					
				}else {
					//添加过,更新,数量加一 Tjsl
					for (int i = 0; i < list_cart.size(); i++) {
						if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + 1);//数量加一 Tjsl
							list_cart.get(i).setHj(list_cart.get(i).getSku_jg() * list_cart.get(i).getTjshl());
						}
					}
				}
				//覆盖客户端的cookie
			}
			Cookie cookie = new Cookie("list_cart_cookie",MyJsonUtil.list_to_json(list_cart));
			cookie.setMaxAge(60*60*24);
			response.addCookie(cookie);
		}else {
			//用户已登录
			//从session中获取list_cart_session
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute(list_cart_cookie);
			//根据id查出db中是否有数据
			List<T_MALL_SHOPPINGCAR>shoppingCarts = shoppingCartServiceInf.get_shoppingCart_by_user_id(user.getId());
			
			if (shoppingCarts==null||shoppingCarts.size()==0) {
				//用户db数据库中没购物车,或者购物车为空,添加cart
				cart.setYh_id(user.getId());
				shoppingCartServiceInf.add_shoppingCart(cart);
			}else {
				//购物车中有商品,判断新的这个是否和购物车中的商品重复,不重复就添加,重复就update
				for (T_MALL_SHOPPINGCAR t_MALL_SHOPPINGCAR : shoppingCarts) {
					if (t_MALL_SHOPPINGCAR.equals(cart)) {
						//重复,update
						shoppingCartServiceInf.update_shoppingCart(cart);
					}else {
						//不重复,添加
						shoppingCartServiceInf.add_shoppingCart(cart);
					}
				}
			}
			
			
			
			
				// 从session中获得购物车数据
				list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");

				// list_cart = shoppingCartServiceInf.get_list_cart_by_user(user);
				// 判断db是否为空
				if (list_cart == null || list_cart.size() == 0) {
					// 添加db
					shoppingCartServiceInf.add_shoppingCart(cart);
					// 同步session
					list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
					list_cart.add(cart);
					session.setAttribute("list_cart_session", list_cart);
				} else {
					//session不为null
					boolean b = if_new_cart(list_cart, cart);
					if (b) {
						// 添加db
						shoppingCartServiceInf.add_shoppingCart(cart);
						// 同步session
						list_cart.add(cart);
					} else {
						// 更新db
						// 同步session
						for (int i = 0; i < list_cart.size(); i++) {
							if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
								list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + 1);
								list_cart.get(i).setHj(list_cart.get(i).getSku_jg() * list_cart.get(i).getTjshl());
								shoppingCartServiceInf.update_shoppingCart(list_cart.get(i));
							}
						}
					}
				}
		}
		//把session中的数据同步到db中
		
		
		
		return "sale_cart_success";
	}*/
	
	
	@RequestMapping("add_cart")
	public String add_cart(HttpServletResponse response, HttpSession session, T_MALL_SHOPPINGCAR cart,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			HttpServletRequest request, ModelMap map) {

		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		// 购物车添加代码

		// 判断用户是否登录
		if (user == null) {
			// 判断cookie是否为空
			if (list_cart_cookie == null || list_cart_cookie.equals("")) {
				// 添加cookie
				list_cart.add(cart);
			} else {
				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				boolean b = if_new_cart(list_cart, cart);
				if (b) {
					// 添加cookie
					list_cart.add(cart);
				} else {
					// 更新cookie
					for (int i = 0; i < list_cart.size(); i++) {
						if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + 1);
							list_cart.get(i).setHj(list_cart.get(i).getSku_jg() * list_cart.get(i).getTjshl());
						}
					}
				}
			}

			// 添加cookie
			Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);

		} else {
			// 从session中获得购物车数据
			list_cart = (List<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");

			// list_cart = shoppingCartServiceInf.get_list_cart_by_user(user);
			// 判断db是否为空
			if (list_cart == null || list_cart.size() == 0) {
				// 添加db
				shoppingCartServiceInf.add_shoppingCart(cart);
				// 同步session
				list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
				list_cart.add(cart);
				session.setAttribute("list_cart_session", list_cart);
			} else {
				boolean b = if_new_cart(list_cart, cart);
				if (b) {
					// 添加db
					shoppingCartServiceInf.add_shoppingCart(cart);
					// 同步session
					list_cart.add(cart);
				} else {
					// 更新db
					// 同步session
					for (int i = 0; i < list_cart.size(); i++) {
						if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
							list_cart.get(i).setTjshl(list_cart.get(i).getTjshl() + 1);
							list_cart.get(i).setHj(list_cart.get(i).getSku_jg() * list_cart.get(i).getTjshl());
							shoppingCartServiceInf.update_shoppingCart(list_cart.get(i));
						}
					}
				}
			}
		}

		return "sale_cart_success";
	}

	private boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;

		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				// 说明之前添加过
				b = false;
			}
		}
		return b;
		
	}
	
	//获取购物车数据
	
	//用户登录,和未登录两种情况
	@RequestMapping("get_miniCart")
	public String get_miniCart(ModelMap map,HttpSession session,@CookieValue(value = "list_cart_cookie",required=false) String list_cart_cookie) {
		T_MALL_USER_ACCOUNT user= (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		ArrayList<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		
		if (user == null) {
			//没登录,从cookie中拿数据
			list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) MyJsonUtil.json_to_list(list_cart_cookie,T_MALL_SHOPPINGCAR.class );
		}else {
			//已登录,从session中拿数据
			list_cart  = (ArrayList<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		map.put("list_cart", list_cart);
		//map.put("sum", get_sum(list_cart));
	
		return "sale_miniCart_list_inner";
	}

//迷你购物车页面
	@RequestMapping("goto_cart_list")
	public String goto_cart_list(ModelMap map,HttpSession session,@CookieValue(value = "list_cart_cookie",required=false) String list_cart_cookie) {
		T_MALL_USER_ACCOUNT user= (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		ArrayList<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		
		if (user == null) {
			//没登录,从cookie中拿数据
			list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) MyJsonUtil.json_to_list(list_cart_cookie,T_MALL_SHOPPINGCAR.class );
			
		}else {
			//已登录,从session中拿数据
			list_cart  = (ArrayList<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		
		
		map.put("sum", get_sum(list_cart));
		
		if (map==null) {
			map.put("sum", new BigDecimal("0"));
		}
		map.put("list_cart", list_cart);
		
		
		
		return "sale_cart_list";
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

//更改购物车是否被选中的状态
	@RequestMapping("change_cart_status")
	public String change_cart_status(int sku_id, String shfxz,@CookieValue(value="list_cart_cookie",required=false) String list_cart_cookie,
			HttpServletResponse response,T_MALL_SHOPPINGCAR cart,HttpSession session,HttpServletRequest request, ModelMap map) {
		T_MALL_USER_ACCOUNT user = (T_MALL_USER_ACCOUNT) session.getAttribute("user");
		ArrayList<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		
		//修改购物车状态
		//先拿到购物车购物车中的数据,然后再根据条件更新
		
		if (user == null) {
			list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
		}else {
			list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) session.getAttribute("list_cart_session");
		}
		
		for (int i = 0; i < list_cart.size(); i++) {
			
			if (sku_id == list_cart.get(i).getSku_id()) {
				list_cart.get(i).setShfxz(shfxz);
			}
			//更新数据库
			if (user==null) {
				//把list存入cookie
				Cookie cookie = new Cookie("list_cart_cookie", MyJsonUtil.list_to_json(list_cart));
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
			}else {
				//更新db
				shoppingCartServiceInf.update_shoppingCart(list_cart.get(i));
				
			}
		}
		map.put("list_cart", list_cart);
		map.put("sum", get_sum(list_cart));
		
		return "sale_cart_list_inner";
	}
	
}
