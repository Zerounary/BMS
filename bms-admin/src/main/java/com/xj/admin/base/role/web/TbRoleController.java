package com.xj.admin.base.role.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.xj.admin.base.common.model.JSTreeEntity;
import com.xj.admin.base.dept.service.ITbDeptService;
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.base.resource.entity.TbResource;
import com.xj.admin.base.resource.service.ITbResourceService;
import com.xj.admin.base.role.entity.TbRole;
import com.xj.admin.base.role.service.ITbRoleService;
import com.xj.admin.util.TreeUtil;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.common.base.common.util.JsonUtil;

/**
 * <p>
 * 角色表  前端控制器
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
@Controller
@RequestMapping("/role/")
public class TbRoleController extends BaseController{
	
	@Autowired
	private ITbRoleService roleService;
	
	@Autowired
	private ITbResourceService resourceService;
	
	@Autowired
	private ITbDeptService deptService;
	
	@GetMapping("listUI")
    public String listUI() {
		return "role/list";
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
		if(Validator.isNotNullOrEmpty(parameters.get("deptId"))){
			deptId = Integer.parseInt(parameters.get("deptId").toString());
		}
		Page<TbRole> list = roleService.selectRoleList(new Page<TbRole>(pager.getNowPage(), pager.getPageSize()),deptId);
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@GetMapping("form")
    public String form(Map<String,Object> map) {
		return "role/form";
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(TbRole role){
		if(role.getId()==null){
			role.setCreateTime(new Date(System.currentTimeMillis()));
			role.setUpdateTime(new Date(System.currentTimeMillis()));
		}else
		{
			role.setUpdateTime(new Date(System.currentTimeMillis()));
		}
		
		if(!roleService.insertOrUpdate(role)){
			return fail(EnumSvrResult.ERROR);
		}
		return success();
	}
	
	@DeleteMapping("{roleId}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer roleId) {	
		if(!roleService.deleteRoleResource(roleId)){
			return fail(EnumSvrResult.ERROR_DELETE_ROLE);
		}
		return success();
    }	
	
	@GetMapping("{roleId}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer roleId) {	
		TbRole role = roleService.selectById(roleId);
		role.setDept(deptService.selectById(role.getDeptId()));
		map.put("role", role);
		return "role/edit";
    }	
	
	@GetMapping("{roleId}/permission")
    public String permission(Map<String,Object> map,@PathVariable(required=true) Integer roleId) {	
		TbRole role = roleService.selectById(roleId);
		List<TbResource> resources = resourceService.queryResourceList(ConvertUtil.toMap("isHide",(Object)0,"roleId",(Object)roleId));
		List<JSTreeEntity> jstreeList = new TreeUtil().generateJSTree(resources);
		map.put("role", role);
		map.put("resources", jstreeList);
		return "role/permission";
    }	
	
	@GetMapping("{roleId}/getPermission")
	@ResponseBody
    public Object getPermission(@PathVariable(required=true) Integer roleId) {	
		List<TbResource> resources = resourceService.queryResourceList(ConvertUtil.toMap("isHide",(Object)0,"roleId",(Object)roleId));
		//List<JSTreeEntity> jstreeList = new TreeUtil().generateJSTree(resources);
		return resources;
    }	
	
	@PostMapping("savePermission")
	@ResponseBody
	public AbstractBean permission(int roleId, @RequestParam("resourceIds[]") List<Integer> resourceIds){
		roleService.savePermission(roleId,resourceIds);
		return success();
	}
}
