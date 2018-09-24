package com.xj.admin.base.dept.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xj.admin.base.dept.entity.TbDept;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xj
 * @since 2017-05-08
 */
public interface ITbDeptService extends IService<TbDept> {
	
	Page<TbDept> selectDeptsPage(Page<TbDept> page,Wrapper<TbDept> wrapper);
	
	Page<TbDept> selectDeptList(Page<TbDept> page,Integer deptId);
	
	TbDept selectDeptsById(Integer id);
	
}
