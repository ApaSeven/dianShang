package com.atguigu.service;

import java.util.List;


import com.atguigu.bean.OBJECT_T_MALL_ATTR;

public interface AttrServiceInf {

	void save_attr(int flbh2, List<OBJECT_T_MALL_ATTR> list);

	List<OBJECT_T_MALL_ATTR> get_attr_by_class_2_id(int class_2_id);
	
	//根据属性id,查出下边的属性值id
	List<Integer> get_list_value_id_by_attr_id(int attr_id);

}
