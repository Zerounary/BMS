package com.xj.admin.base.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xj.admin.base.dict.entity.TbDict;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xj
 * @since 2017-06-01
 */
public interface TbDictMapper extends BaseMapper<TbDict> {
	
	public List<TbDict> selectDictPage(Pagination page,@Param("ew") Wrapper<TbDict> wrapper);
	
}