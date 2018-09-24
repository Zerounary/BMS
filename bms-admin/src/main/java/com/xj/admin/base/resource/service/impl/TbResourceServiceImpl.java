package com.xj.admin.base.resource.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.feilong.core.bean.ConvertUtil;
import com.xj.admin.base.resource.entity.TbResource;
import com.xj.admin.base.resource.mapper.TbResourceMapper;
import com.xj.admin.base.resource.service.ITbResourceService;
import com.xj.admin.base.resourcerole.mapper.TbResourcesRoleMapper;
import com.xj.admin.util.TreeUtil;

/**
 * <p>
 * 资源表  服务实现类
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
@Service
public class TbResourceServiceImpl extends ServiceImpl<TbResourceMapper, TbResource> implements ITbResourceService {

	@Autowired
	private TbResourceMapper resourceMapper;
	
	@Autowired
	private TbResourcesRoleMapper resourceRoleMapper;
	
	@Override
	public List<TbResource> findResourcesByUserId(int userId) {
		return resourceMapper.findResourcesByUserId(userId);
	}

	@Override
	public List<TbResource> findResourcesMenuByUserId(int userId) {
		List<TbResource> resources = resourceMapper.findResourcesMenuByUserId(userId);
		List<TbResource> treeMenuList =new TreeUtil().treeMenuList(resources, 0);
		return treeMenuList;
	}

	@Override
	public List<TbResource> queryResourceList(Map<String, Object> parameter) {
		return resourceMapper.queryResourceList(parameter);
	}

	@Override
	@Transactional
	public void deleteRoleResource(int resourceId) {
		resourceRoleMapper.deleteByMap(ConvertUtil.toMap("s_id",(Object)resourceId));
		resourceMapper.deleteById(resourceId);
		
	}

	@Override
	public Page<TbResource> selectResourcePage(Page<TbResource> page, Wrapper<TbResource> wrapper) {
		page.setRecords(resourceMapper.selectResourcePage(page, wrapper));
		return page;
	}
}
