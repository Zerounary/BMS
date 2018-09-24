package com.xj.admin.base.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xj.admin.base.dict.entity.TbDict;
import com.xj.admin.base.dict.mapper.TbDictMapper;
import com.xj.admin.base.dict.service.ITbDictService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xj
 * @since 2017-06-01
 */
@Service
public class TbDictServiceImpl extends ServiceImpl<TbDictMapper, TbDict> implements ITbDictService {

	@Autowired
	private TbDictMapper dictMapper;
	
	@Override
	public Page<TbDict> selectDictPage(Page<TbDict> page, Wrapper<TbDict> wrapper) {
		page.setRecords(dictMapper.selectDictPage(page, wrapper));
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> selectDictListByCode(String code) {
		Map<Integer,String> codeDicts = new HashMap<>();
		List<TbDict> dicts = dictMapper.selectList(Condition.create().eq("code", code).ne("num", 0).orderBy("num"));
		for(TbDict dict:dicts){
			codeDicts.put(dict.getNum(), dict.getName());
		}
		return codeDicts;
	}
	
}
