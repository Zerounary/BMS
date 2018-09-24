package com.xj.admin.bussiness.news.web;

import java.util.Date;
import java.util.HashMap;
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
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.bussiness.news.service.ITbNewsService;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.common.base.common.util.JsonUtil;
import com.xj.common.bussiness.news.entity.TbNews;

/**
 * <p>
 * 栏目管理  前端控制器
 * </p>
 *
 * @author xj
 * @since 2017-01-05
 */
@Controller
@RequestMapping("/news/")

public class TbNewsController extends BaseController {

	@Autowired
	private ITbNewsService newsService;
	
	@GetMapping("listUI")
    public String listUI() {
		return "news/list";
    }
	
	@SuppressWarnings("unchecked")
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
		Page<TbNews> list = newsService.selectPage(new Page<TbNews>(pager.getNowPage(), pager.getPageSize()), Condition.create().allEq(parameters).orderBy("id",false));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@GetMapping("form")
    public String form(Map<String,Object> map) {
		return "news/form";
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(TbNews tbnews){
		AbstractBean bean = new AbstractBean();
			if(tbnews.getId()==null){
				tbnews.setCreateTime(new Date());
				tbnews.setCreateUser(getUserEntity().getAccountName());
				tbnews.setUpdateTime(new Date());
			}else{
				tbnews.setUpdateTime(new Date());
				tbnews.setUpdateUser(getUserEntity().getAccountName());
			}
			tbnews.setIsShow(tbnews.getIsShow()==null?"2":"1");
			if(!newsService.insertOrUpdate(tbnews)){
				bean.setStatus(EnumSvrResult.ERROR.getVal());
				bean.setMessage(EnumSvrResult.ERROR.getName());
			}
		return bean;
	}
	
	@DeleteMapping("{id}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer id) {	
		if(!newsService.deleteById(id)){
			return fail(EnumSvrResult.ERROR);
		}
		return success();
    }	
	
	@GetMapping("{id}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer id) {	
		TbNews tbnews = newsService.selectById(id);
		map.put("record", tbnews);
		return "news/edit";
    }	
	
	@DeleteMapping("{ids}/deleteBatch")
	@ResponseBody
	public AbstractBean deleteBatch(@PathVariable(required=true) String ids) {
		if(!newsService.deleteBatchIds(ConvertUtil.toList(ids.split(",")))){
			return fail(EnumSvrResult.ERROR);
		}
		return success();
	}
}
