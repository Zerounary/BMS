package com.xj.admin.base.resource.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.xj.admin.base.common.model.Select2Entity;
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.base.resource.entity.TbResource;
import com.xj.admin.base.resource.service.ITbResourceService;
import com.xj.admin.util.TreeUtil;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.common.base.common.util.JsonUtil;

/**
 * <p>
 * 资源表  前端控制器
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
@Controller
@RequestMapping("/resource/")
public class TbResourceController extends BaseController{
	
	@Autowired
	private ITbResourceService resourceService;
	
	@GetMapping("listUI")
    public String listUI() {
	
		return "resource/list";
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping("list")
	@ResponseBody
    public Object list(String gridPager) {
		Pager pager = JsonUtil.getObjectFromJson(gridPager, Pager.class);
		Map<String, Object> parameters = null;
		if(Validator.isNullOrEmpty(pager.getParameters())){
			parameters = new HashMap<>();
			parameters.put("s_parent_id", 0);
		}else{
			parameters = pager.getParameters();
			if(Validator.isNotNullOrEmpty(parameters.get("parentId"))){
				parameters.put("s_parent_id", parameters.get("parentId"));
			}
		}
		
		parameters.remove("parentId");
		Page<TbResource> list = resourceService.selectResourcePage(new Page<TbResource>(pager.getNowPage(), pager.getPageSize()), Condition.create().isWhere(true).allEq(parameters));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	
	@GetMapping("form")
    public String form(Map<String,Object> map) {
		List<TbResource> list = resourceService.selectByMap(null);
		List<Select2Entity> select2List = new TreeUtil().getSelectTree(list, 0);
		map.put("resources", select2List);
		return "resource/form";
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(TbResource resource){
		resource.setParentId(Validator.isNullOrEmpty(resource.getParentId())?0:resource.getParentId());
		if(resource.getId()==null){
			resource.setCreateTime(new Date(System.currentTimeMillis()));
			resource.setUpdateTime(new Date(System.currentTimeMillis()));
		}else
		{
			resource.setUpdateTime(new Date(System.currentTimeMillis()));
		}
		
		if(!resourceService.insertOrUpdate(resource)){
			return fail(EnumSvrResult.ERROR);
		}
		return success();
	}
	
	@DeleteMapping(value="{id}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer id) {	
		List<TbResource> childrens = resourceService.selectByMap(ConvertUtil.toMap("s_parent_id",(Object)id));
		if(childrens!=null && childrens.size()>0){
			return fail(EnumSvrResult.ERROR_RESOURCE_DELETE);
		}else{
			resourceService.deleteRoleResource(id);
		}
		return success();
    }	
	
	@GetMapping(value="{id}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer id) {	
		TbResource resource = resourceService.selectById(id);
		List<TbResource> list = resourceService.selectByMap(null);
		List<Select2Entity> select2List = new TreeUtil().getSelectTree(list, 0);
		map.put("resources", select2List);
		map.put("resource", resource);
		return "resource/edit";
    }	
	
	@GetMapping("icon")
    public String icon() {
		return "resource/icon";
    }
	
}
