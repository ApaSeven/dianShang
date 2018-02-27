package com.wch.mapperTest;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.google.gson.Gson;
import com.wch.bean.T_MALL_CLASS_1;
import com.wch.bean.T_MALL_CLASS_2;
import com.wch.bean.T_MALL_TRADE_MARK;
import com.wch.mapper.Class1Mapper;
import com.wch.mapper.Class2Mapper;
import com.wch.mapper.TMMapper;

public class MapperTest {

	@Test
	public void test() throws Exception {
		
		//sqlSessionFactory
		
		//sqlsessionFactory
			String resource = "mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		
		//获取到session
			SqlSession session = sqlSessionFactory.openSession();
		
		//获取到接口的实例对象
			Class1Mapper class1Mapper = session.getMapper(Class1Mapper.class);
			
		//调用方法获取数据库class1中的值list
			List<T_MALL_CLASS_1> class1_all = class1Mapper.select_class1_all();
		//把list转化成json
		Gson gson = new Gson();
		String class1str = gson.toJson(class1_all);
		System.out.println(class1str);
		//生成文件
		FileOutputStream out = new FileOutputStream("F:/TT/class_1.js");
		out.write(class1str.getBytes());
		out.close();
		
		
		//二级分类
		//获取mapper
		Class2Mapper class2Mapper = session.getMapper(Class2Mapper.class);
		//遍历取出1级分类的ID
		for (T_MALL_CLASS_1 t_MALL_CLASS_1 : class1_all) {
			int class1_id = t_MALL_CLASS_1.getId();
			
			//调用方法查询出所有的class2的数据
			List<T_MALL_CLASS_2> class2_all = class2Mapper.select_class2_all(class1_id);
			//转json数据
			Gson gson2 = new Gson();
			String class2str = gson2.toJson(class2_all);
			System.out.println(class2str);
			
			//把获取的json创建成文件放到指定目录
			//文件命名规则class_2_(1级分类id).js,用文件流写过去
			out = new FileOutputStream("F:/TT/class_2_"+class1_id+".js");
			out.write(class2str.getBytes());
			out.close();
			
			
		}
		
		//3级目录,商标
		//获取mapper
		TMMapper tmMapper = session.getMapper(TMMapper.class);
		
		//遍历
		for (T_MALL_CLASS_1 t_MALL_CLASS_1 : class1_all) {
			Integer class1_id = t_MALL_CLASS_1.getId();
			
			//调用方法查询
			List<T_MALL_TRADE_MARK> tm_all = tmMapper.select_tm_all(class1_id);
			
			//转换成json
			Gson gson2 = new Gson();
			String strTm_all = gson2.toJson(tm_all);
			
			//用文件流
			out = new FileOutputStream("F:/TT/tm_class_1_"+class1_id+".js");
			out.write(strTm_all.getBytes());
			out.close();
			
		}
		
		//关闭session
		session.close();
		
	
	}

}
