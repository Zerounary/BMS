package com.xj.admin.base.dict.service;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xj.admin.base.dict.entity.TbDict;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xj
 * @since 2017-06-01
 */
public interface ITbDictService extends IService<TbDict> {
	
	public Page<TbDict> selectDictPage(Page<TbDict> page, Wrapper<TbDict> wrapper);
	
	public Map<Integer,String> selectDictListByCode(String code);
	
}
