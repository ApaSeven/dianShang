package com.wch.mapper;

import java.util.List;

import com.wch.bean.T_MALL_CLASS_1;
import com.wch.bean.T_MALL_CLASS_2;
import com.wch.bean.T_MALL_TRADE_MARK;

public interface TMMapper {
	
	//查询一级目录的所有数据
	List<T_MALL_TRADE_MARK> select_tm_all(Integer class1_id);
	
}
