package com.atguigu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.http.HttpResponse;
import org.opensaml.xml.security.x509.X509Util.ENCODING_FORMAT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.bean.T_MALL_ADDRESS;
import com.atguigu.bean.T_MALL_SHOPPINGCAR;
import com.atguigu.bean.T_MALL_USER_ACCOUNT;
import com.atguigu.server.AddressServer;
import com.atguigu.server.UserServer;
import com.atguigu.service.ShoppingCartServiceInf;
import com.atguigu.util.MyJsonUtil;
import com.atguigu.util.MyPropertiesUtil;
import com.atguigu.util.MyWsFactorybean;
import com.atguigu.ws.TestWsInf;

@Controller

public class LoginController {
	
	
	
	@Autowired
	ShoppingCartServiceInf shoppingCartServiceImpl;
	
	@Autowired
	UserServer userServer;
	
	@Autowired
	TestWsInf testWsInf;
	
	@Autowired
	AddressServer addressServer;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	ActiveMQQueue queueDestination;
	
	
	//想自动注入,调用远程接口的代理类
	//UserServer userServer = MyWsFactorybean.getMyWs(MyPropertiesUtil.getMyProperty("ws.properties","user_url"), UserServer.class);
	
	//登录  同步数据
	@RequestMapping("login")
	public String login(String dataSource_type ,@CookieValue(value = "list_cart_cookie",required=false)String list_cart_cookie,T_MALL_USER_ACCOUNT user,HttpSession session,HttpServletResponse response) {
		//测试安全接口
		testWsInf.ping("交易系统:安全接口测试");
		
		T_MALL_USER_ACCOUNT select_user=null;
		//调用远程借口,验证登录
		//判断向那个数据源查数据
		if (dataSource_type.equals("1")) {
			select_user = userServer.login(user);//要抓异常,防止远程调用的那个借口出错
			//调用远程借口获得用户地址
			List<T_MALL_ADDRESS> get_addresses_by_user_id = addressServer.get_addresses_by_user_id(select_user);
			
			System.out.println(get_addresses_by_user_id);
		
		}else {
			select_user = userServer.login2(user);
		}
		
		//创建调用远程接口的代理类
	//	UserServer userServer = MyWsFactorybean.getMyWs(MyPropertiesUtil.getMyProperty("ws.properties","user_url"), UserServer.class);
		
		if (select_user==null) {
			return "goto_login.do";
		}else {
			session.setAttribute("user", select_user);
			//向cookie写入用户登录信息
			String yh_nch = select_user.getYh_nch();
			
			//创建cookie
			Cookie cookie = null;
			try {
				cookie = new Cookie("yh_nch", URLEncoder.encode(yh_nch, "utf-8"));
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			//把cookie放回客户端,设置过期时间
			
		
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
		
			response.addCookie(cookie);
			
			//合并购物车
			//cookie中的数据是不包含用户ID的
			//
			List<T_MALL_SHOPPINGCAR>list_cart_db  = shoppingCartServiceImpl.get_shoppingCart_by_user_id(user.getId());
			merge_cart(list_cart_db,list_cart_cookie,response,session,select_user);
			
			//调用日志服务,储存日志
			//发送消息给mq
			
			final int userid = select_user.getId();
			final String yh_mch = select_user.getYh_mch();
			
			jmsTemplate.send(queueDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage("yh_id"+userid+"yh_mch"+yh_mch);
				}
			});

		}
		
		return "redirect:/index.do";
	}
	private void merge_cart(List<T_MALL_SHOPPINGCAR> list_cart_db, String list_cart_cookie,
			HttpServletResponse response, HttpSession session, T_MALL_USER_ACCOUNT user) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		if (list_cart_db == null || list_cart_db.size() == 0) {
			// 数据库中没有购物车数据
			if (list_cart_cookie==null||list_cart_cookie.equals("")) {
				// 结束
			} else {
				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				for (int i = 0; i < list_cart.size(); i++) {
					// 将cookie中的数据插入db
					list_cart.get(i).setYh_id(user.getId());
					shoppingCartServiceImpl.add_shoppingCart(list_cart .get(i));;
				}
			}
		} else {
			// 数据库中有购物车数据
			if (list_cart_cookie==null||list_cart_cookie.equals("")) {
				// 结束
			} else {
				list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);

				for (int i = 0; i < list_cart.size(); i++) {

					boolean b = if_new_cart(list_cart_db, list_cart.get(i));

					if (b) {
						// 插入db
						list_cart.get(i).setYh_id(user.getId());
						shoppingCartServiceImpl.get_shoppingCart_by_user_id(user.getId());
					} else {
						// 更新db
						list_cart.get(i).setYh_id(user.getId());
						shoppingCartServiceImpl.update_shoppingCart(list_cart.get(i));
					}
				}
			}
		}

		// 清空cookie，同步session
		response.addCookie(new Cookie("list_cart_cookie", ""));

		session.setAttribute("list_cart_session", shoppingCartServiceImpl.get_shoppingCart_by_user_id(user.getId()));
	}

	/*private void merge_cart(List<T_MALL_SHOPPINGCAR> list_cart_db, String list_cart_cookie, HttpServletResponse response, HttpSession session,
			T_MALL_USER_ACCOUNT user) {
		
		ArrayList<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		
		if (list_cart_db ==null||list_cart_db.size()==0) {
			//数据库中没值
			if (list_cart_cookie==null||list_cart_cookie.equals("")) {
				//cookie中也没值,就什么都不用做
				
			}else {
				//数据库中没数据,cookie中有数据,向数据库中插入数据
				for (int i = 0; i < list_cart.size(); i++) {
					list_cart.get(i).setYh_id(user.getId());
					shoppingCartServiceImpl.add_shoppingCart(list_cart.get(i));
				}
				list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) MyJsonUtil.json_to_list(list_cart_cookie,T_MALL_SHOPPINGCAR.class );
				for (int i = 0; i < list_cart.size(); i++) {
					shoppingCartServiceImpl.add_shoppingCart(list_cart.get(i));
				}
			}
		}else {
			//-----数据库中有购物车数据
			if (list_cart_cookie==null||list_cart_cookie.equals("")) {
				//cookie中没有数据---结束
			}else {
				
				//db有 cookie有
				
				list_cart = (ArrayList<T_MALL_SHOPPINGCAR>) MyJsonUtil.json_to_list(list_cart_cookie,T_MALL_SHOPPINGCAR.class );
				//list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);
				for (int i = 0; i < list_cart.size(); i++) {
					//判断cookie中的数据是否和db中的数据重复
					boolean b = if_new_cart(list_cart_db,list_cart.get(i));
					
					if (b) {
						//true代表是新的,没有重复
						//插入db
						list_cart.get(i).setYh_id(user.getId());
						shoppingCartServiceImpl.add_shoppingCart(list_cart.get(i));
					}else {
						//重复update 
						//更新数量
						
						
						
						if (list_cart.size()>=list_cart_by_id.size()) {
							for (int j = 0; j < list_cart.size(); j++) {
								if (list_cart.get(i).getSku_id()==list_cart_by_id.get(i).getSku_id()) {
									list_cart.get(i).setTjshl(list_cart.get(i).getTjshl()+list_cart_by_id.get(i).getTjshl());
									list_cart.get(i).setYh_id(user.getId());
									shoppingCartServiceImpl.update_shoppingCart(list_cart.get(i));
								}
							}
						}else {
							for (int j = 0; j < list_cart_by_id.size(); j++) {
								if (list_cart.get(i).getSku_id()==list_cart_by_id.get(i).getSku_id()) {
									list_cart.get(i).setTjshl(list_cart.get(i).getTjshl()+list_cart_by_id.get(i).getTjshl());
									list_cart.get(i).setYh_id(user.getId());
									shoppingCartServiceImpl.update_shoppingCart(list_cart.get(i));
								}
							}
						}
						
						
						
					}
				}
			}
		}
		//清空cookie,同步session
		response.addCookie(new Cookie("lise_cart_cookie", ""));
		List<T_MALL_SHOPPINGCAR> list = shoppingCartServiceImpl.get_shoppingCart_by_user_id(user.getId());
		
		session.setAttribute("list_cart_session", list);
		
	}*/

	private boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getSku_id()==cart.getId()) {
				return false;
			}
		}
		return b;
	}
	
	
	@RequestMapping("goto_out")
	public String goto_out(HttpSession session) {
		session.invalidate();//清空session
		
		return "redirect:goto_login.do";
	}
	
	//去登录页面
		@RequestMapping("goto_login")
		public String goto_login() {
			return "sale_login";
		}

}
