package com.xj.admin.base.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xj.admin.base.role.entity.TbRole;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
public interface TbRoleMapper extends BaseMapper<TbRole> {
	
	public List<TbRole> selectRoleList(Pagination page,@Param("deptId") Integer deptId);
	
	public List<TbRole> selectRoleList(@Param("deptId") Integer deptId);
}