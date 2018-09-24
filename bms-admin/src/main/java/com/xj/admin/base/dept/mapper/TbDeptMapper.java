package com.xj.admin.base.dept.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xj.admin.base.dept.entity.TbDept;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xj
 * @since 2017-05-08
 */
public interface TbDeptMapper extends BaseMapper<TbDept> {
	
	public List<TbDept> selectDeptsPage(Pagination page,@Param("ew") Wrapper<TbDept> wrapper);
	
	public List<TbDept> selectDeptList(Page<TbDept> page,@Param("deptId") Integer deptId);
	
	public TbDept selectDeptsById(@Param("id") Integer id);
}