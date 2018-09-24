package com.xj.admin.base.dept.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xj.admin.base.dept.entity.TbDept;
import com.xj.admin.base.dept.mapper.TbDeptMapper;
import com.xj.admin.base.dept.service.ITbDeptService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xj
 * @since 2017-05-08
 */
@Service
public class TbDeptServiceImpl extends ServiceImpl<TbDeptMapper, TbDept> implements ITbDeptService {
	
	@Autowired
	private TbDeptMapper deptMapper;

	@Override
	public Page<TbDept> selectDeptsPage(Page<TbDept> page,Wrapper<TbDept> wrapper) {
		page.setRecords(deptMapper.selectDeptsPage(page, wrapper));
		return page;
	}

	@Override
	public TbDept selectDeptsById(Integer id) {
		return deptMapper.selectDeptsById(id);
	}

	@Override
	public Page<TbDept> selectDeptList(Page<TbDept> page, Integer deptId) {
		page.setRecords(deptMapper.selectDeptList(page, deptId));
		return page;
	}
	
}
