package com.xj.admin.base.user.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xj.admin.base.user.entity.TbUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
public interface ITbUserService extends IService<TbUser> {
	
	public Page<TbUser> selectUserList(Page<TbUser> page,String name,Integer deptId);
	
	public TbUser selectUserRole(Map<String, Object> parameter);
	
	public boolean insertAll(TbUser user);
	
	public boolean updateAll(TbUser user);
	
	public boolean delUser(Integer userId);
}
