package com.atguigu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.T_MALL_PRODUCT;
import com.atguigu.mapper.SpuMapper;

@Service
public class SupServiceImp implements SpuServiceInf {
	
	@Autowired
	SpuMapper spuMapper;

	@Override
	public void save_spu(T_MALL_PRODUCT spu, List<String> list_image) {
		spu.setShp_tp(list_image.get(0));
		//插入spu,返回主键
		spuMapper.insert_spu(spu);
		
		
		//插入图片
		Map<Object,Object>map = new HashMap<Object,Object>();
		map.put("spu_id", spu.getId());
		map.put("list_image", list_image);
		spuMapper.insert_images(map);
		
	}

	//获取spu,根据class_2_id,pp_id
	@Override
	public List<T_MALL_PRODUCT> get_spu_list(int class_2_id, int pp_id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("class_2_id", class_2_id);
		map.put("pp_id", pp_id);
		
		return spuMapper.select_spu_list(map);
		
		
	}

}
