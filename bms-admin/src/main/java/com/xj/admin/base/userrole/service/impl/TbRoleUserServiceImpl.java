package com.xj.admin.base.userrole.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xj.admin.base.userrole.entity.TbRoleUser;
import com.xj.admin.base.userrole.mapper.TbRoleUserMapper;
import com.xj.admin.base.userrole.service.ITbRoleUserService;

/**
 * <p>
 * 用户角色映射表  服务实现类
 * </p>
 *
 * @author xj
 * @since 2016-12-26
 */
@Service
public class TbRoleUserServiceImpl extends ServiceImpl<TbRoleUserMapper, TbRoleUser> implements ITbRoleUserService {
	
}
