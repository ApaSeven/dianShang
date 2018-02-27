package com.atguigu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {
	
	public static void main(String[] args) {
		//时间的显示格式
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		
		String format = sdf.format(new Date());
		
		System.out.println(format);
		
		//时间的加减,或的当前时间3天后的时间
		/*Calendar c = Calendar.getInstance();
		
		c.add(Calendar.DATE, 3);//(天  , 3)  (天  , -3)
		
		Date time = c.getTime();
		
		System.out.println(time);*/
		
	}
	
	public static String getMyTime(Date date,String formate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		
		String format = sdf.format(date);
		
		return formate;
		
	}
	
	public static Date getMyDate(int i) {
		
		Calendar c = Calendar.getInstance();
		
		c.add(Calendar.DATE, i);//(天  , 3)  (天  , -3)
		
		Date time = c.getTime();
		
		return time;
	}
	
}	
