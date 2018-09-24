package com.xj.admin.base.resource.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xj.admin.base.resource.entity.TbResource;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
public interface ITbResourceService extends IService<TbResource> {
	
	public List<TbResource> findResourcesByUserId(int userId);
	
	public List<TbResource> findResourcesMenuByUserId(int userId);
	
    public List<TbResource> queryResourceList(Map<String, Object> parameter);
    
	public Page<TbResource> selectResourcePage(Page<TbResource> page, Wrapper<TbResource> wrapper);
	
	public void deleteRoleResource(int resourceId);
}
