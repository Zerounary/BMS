package com.xj.admin.base.dept.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.xj.admin.base.dept.entity.TbDept;
import com.xj.admin.base.dept.service.ITbDeptService;
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.base.user.entity.TbUser;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.common.base.common.util.JsonUtil;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author xj
 * @since 2017-05-08
 */
@Controller
@RequestMapping("/dept/")

public class TbDeptController extends BaseController {

	@Autowired
	private ITbDeptService deptService;
	
	@GetMapping("listUI")
    public String listUI() {
		return "dept/list";
    }
	
	@RequestMapping("form")
    public String form(Map<String,Object> map) {
		return "dept/form";
    }
    
    @PostMapping("list")
	@ResponseBody
    public Object list(String gridPager) {
		Pager pager = JsonUtil.getObjectFromJson(gridPager, Pager.class);
		Map<String, Object> parameters = null;
		if(Validator.isNullOrEmpty(pager.getParameters())){
			parameters = new HashMap<>();
		}else{
			parameters = pager.getParameters();
		}
		Integer deptId = getUserEntity().getDeptId();
		if(Validator.isNotNullOrEmpty(parameters.get("pid"))){
			deptId = Integer.parseInt(parameters.get("pid").toString());
		}
		Page<TbDept> list = deptService.selectDeptList(new Page<TbDept>(pager.getNowPage(), pager.getPageSize()), deptId);
		makeGridResult(parameters, pager, list);
		return parameters;
    }
    
    @GetMapping("listTree")
	@ResponseBody
    public Object listTree() {
    	TbUser user = getUserEntity();
		@SuppressWarnings("unchecked")
		List<TbDept> list = deptService.selectList(Condition.create().like("pids", user.getDept().getPids()+user.getDeptId()).or("id={0}", user.getDeptId()));
		return list;
    }
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AbstractBean add(TbDept tbdept){
		if(tbdept.getPid()!=0){
			TbDept parentDept = deptService.selectById(tbdept.getPid());
			tbdept.setPids(parentDept.getPids()+tbdept.getPid()+",");
		}
		if(!deptService.insertOrUpdate(tbdept)){
			return error();
		}
		return success();
	}
	
	@RequestMapping(value="{id}/delete",method=RequestMethod.DELETE)
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer id) {	
		boolean result = false;
		try {
			result = deptService.deleteById(id);
		} catch (Exception e) {
			return fail(EnumSvrResult.ERROR_DELETE_DEPT);
		}
		if(result){
			return success();
		}else{
			return error();
		}
    }	
	
	@RequestMapping(value="{id}/select",method=RequestMethod.GET)
    public String select(Map<String,Object> map,@PathVariable Integer id) {	
		TbDept tbdept = deptService.selectDeptsById(id);
		map.put("record",tbdept);
		return "dept/edit";
    }	
	@GetMapping("toSelectTree")
    public String toSelectTree() {	
		return "dept/selectTree";
    }	
	
	
}
