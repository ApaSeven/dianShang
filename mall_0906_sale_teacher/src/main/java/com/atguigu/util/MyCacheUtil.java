package com.atguigu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.atguigu.bean.OBJECT_T_MALL_SKU;


import redis.clients.jedis.Jedis;

public class MyCacheUtil {

	public static <T> List<T> getMyListByKey(String key,Class<T>t) {
		List<T> list_sku = new ArrayList<T>();
		
		Jedis jedis = JedisPoolUtils.getJedis();//调用第三方程序的时候要主动抓取异常
		if (jedis==null) {
			return null;
		}else {
			
			Set<String> zrange = jedis.zrange(key, 0, -1);
			
			Iterator<String> iterator = zrange.iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				T sku = MyJsonUtil.json_to_object(next, t);
				
				list_sku.add(sku);
			}
		}
		return list_sku;
		
	}

	
	//同步缓存
	public static <T> void setMyListByKey(List<T> list, String key) {
		
		Jedis jedis = JedisPoolUtils.getJedis();
		
		if (jedis == null) {
			//set不了,记录日志
		}else {
			for (int i = 0; i < list.size(); i++) {
				
				jedis.zadd(key, i,MyJsonUtil.object_to_json(list.get(i)));//redis里存的是字符串

			}
		}
		
	}

	
	
	//根据属性交叉检索
	public static String interKey(String[] keys) {
		Jedis jedis = JedisPoolUtils.getJedis();
		String new_key = "inter";
		
		if (jedis == null) {
			//记录系统信息
			
		}else {
			for (int i = 0; i < keys.length; i++) {
				new_key = new_key+"_"+keys[i];
			}
			
			//判断拼接的key是否已经存在
			Boolean b = jedis.exists(new_key);
			if (!b) {
				
				jedis.zinterstore(new_key, keys);//两个key下的交集zinterstore
			}
			
		}
		
		
		return new_key;
	}
}
