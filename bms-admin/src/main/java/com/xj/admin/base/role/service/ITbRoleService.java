package com.xj.admin.base.role.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xj.admin.base.role.entity.TbRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
public interface ITbRoleService extends IService<TbRole> {
	
	public void savePermission(Integer roleId,List<Integer> resourceIds);
	
	public boolean deleteRoleResource(Integer roleId);
	
	public Page<TbRole> selectRoleList(Page<TbRole> page,Integer deptId);
	
	public List<TbRole> selectRoleList(Integer deptId);
}
