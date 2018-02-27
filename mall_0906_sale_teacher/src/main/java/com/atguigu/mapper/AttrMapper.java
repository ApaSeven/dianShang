package com.atguigu.mapper;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.OBJECT_T_MALL_ATTR;

public interface AttrMapper {

	void insert_attr(Map<Object, Object> map);

	void insert_values(Map<Object, Object> map2);

	List<OBJECT_T_MALL_ATTR> select_attr_by_class_2_id(int class_2_id);

}
