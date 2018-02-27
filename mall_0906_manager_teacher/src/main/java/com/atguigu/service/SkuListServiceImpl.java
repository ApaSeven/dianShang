package com.atguigu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.DETAIL_T_MALL_SKU;
import com.atguigu.bean.OBJECT_T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU;
import com.atguigu.bean.T_MALL_SKU_ATTR_VALUE;
import com.atguigu.mapper.SkuListMapper;

@Service
public class SkuListServiceImpl implements SkuListServiceInf {
	
	@Autowired
	SkuListMapper skuListMapper;

	@Override
	public List<OBJECT_T_MALL_SKU> get_sku_list_by_class2(int class_2_id) {
		List<OBJECT_T_MALL_SKU> sku_list = skuListMapper.select_sku_list_by_class2(class_2_id);
		return sku_list;
	}

	
	@Override
	public List<OBJECT_T_MALL_SKU> get_sku_list_by_attr(int class_2_id,List<T_MALL_SKU_ATTR_VALUE> list_av) {
		
		//根据属性集合动态拼接属性过滤的sql语句/根据属性查询sku
		
		/*if (list_av!=null&&list_av.size()>0) {
			StringBuffer sql = new StringBuffer();
			sql.append("and sku.id in");
			sql.append("(select sku0.sku.id from");
			
			for (int i = 0; i < list_av.size(); i++) {
				sql.append("select sku_id from t_mall_sku_attr_value where shxm_id="
						+list_av.get(i).getShxm_id()+"and shxzh_id="
						+list_av.get(i).getShxzh_id()+")sku" + i + " ");
				if (list_av.size()>1&&i<(list_av.size() - 1)) {
					sql.append(",");
				}
			}
			
			if (list_av.size()>1) {
				sql.append("where");
				for (int i = 0; i < list_av.size(); i++) {
					if (i<list_av.size()-1) {
						
					}
				}
			}
			
		}*/
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("class_2_id",class_2_id);
		
		
		if (list_av != null && list_av.size() > 0) {
			// 根据分类属性交叉检索的动态sql
			StringBuffer sql = new StringBuffer();
			sql.append(" and sku.id in ");
			sql.append(" (select sku0.sku_id from ");

			// 拼接动态sql
			for (int i = 0; i < list_av.size(); i++) {
				sql.append(" (select sku_id from t_mall_sku_attr_value where shxm_id = " + list_av.get(i).getShxm_id()
						+ " and shxzh_id = " + list_av.get(i).getShxzh_id() + ") sku" + i + " ");
				if (list_av.size() > 1 && i < (list_av.size() - 1)) {
					sql.append(" , ");
				}

			}

			if (list_av.size() > 1) {
				sql.append(" where ");
				for (int j = 0; j < list_av.size(); j++) {

					if (j < list_av.size() - 1) {
						sql.append(" sku" + j + ".sku_id=sku" + (j + 1) + ".sku_id");
					}

					if (list_av.size() > 2 && j < list_av.size() - 2) {
						sql.append(" and ");
					}
				}
			}

			sql.append(" ) ");

			map.put("sql", sql.toString());
		}
		
		List<OBJECT_T_MALL_SKU> list_sku = skuListMapper.select_sku_list_by_attr(map);
		return list_sku;
	}

//查商品详情
	@Override
	public DETAIL_T_MALL_SKU get_sku_detail(int sku_id, int spu_id) {
		Map<Object, Object> map = new HashMap<>();
		map.put("sku_id", sku_id);
		map.put("spu_id", spu_id);
		
		return skuListMapper.get_sku_detail(map);
		
	}


	@Override
	public List<T_MALL_SKU> get_sku_list_by_spu_id(int spu_id) {
		return skuListMapper.get_sku_list_by_spu_id(spu_id);
		
	}

}
