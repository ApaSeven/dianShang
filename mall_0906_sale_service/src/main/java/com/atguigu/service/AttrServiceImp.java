package com.atguigu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.MOEL_T_MALL_ATTR;
import com.atguigu.bean.OBJECT_T_MALL_ATTR;
import com.atguigu.bean.T_MALL_VALUE;
import com.atguigu.mapper.AttrMapper;

@Service
public class AttrServiceImp implements AttrServiceInf{
	
	@Autowired
	AttrMapper attrMapper;

	@Override
	public void save_attr(int flbh2, List<OBJECT_T_MALL_ATTR> list_attr) {
		//属性(力量)
		for (int i = 0; i < list_attr.size(); i++) {
			OBJECT_T_MALL_ATTR attr = list_attr.get(i);
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("flbh2", flbh2);
			map.put("attr",attr);
			attrMapper.insert_attr(map);//插入属性,返回主键
			
		//属性值(多少斤)
			//根据属性主键,批量插入属性值
			Map<Object, Object> map2 = new HashMap<Object, Object>();
			map2.put("attr_id", attr.getId());
			map2.put("list_value", attr.getList_value());
			attrMapper.insert_values(map2);
		}
		
		
	}

	//根据2级目录查询出属性
	@Override
	public List<OBJECT_T_MALL_ATTR> get_attr_by_class_2_id(int class_2_id) {
		
		List<OBJECT_T_MALL_ATTR>list_attr = attrMapper.select_attr_by_class_2_id(class_2_id);
		
		System.out.println("list_attr");
		return list_attr;
	}

}
