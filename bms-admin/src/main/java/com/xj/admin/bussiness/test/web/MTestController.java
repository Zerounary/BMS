package com.xj.admin.bussiness.test.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.xj.admin.base.index.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.admin.bussiness.test.service.IMTestService;
import com.xj.admin.bussiness.test.entity.MTest;
import com.xj.admin.util.JsonUtil;
import com.xj.admin.util.dtgrid.model.Pager;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author xj
 * @since 2018-09-24
 */
@Controller
@RequestMapping("/test/")
public class MTestController extends BaseController {

	@Autowired
	private IMTestService testService;
	
	@GetMapping("listUI")
    public String listUI() {
		return "test/list";
    }
	
	@GetMapping("form")
    public String form(Map<String,Object> map) {
		return "test/form";
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
		Page<MTest> list = testService.selectPage(new Page<MTest>(pager.getNowPage(), pager.getPageSize()), Condition.create().allEq(parameters));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(MTest mtest){
		if(!testService.insertOrUpdate(mtest)){
			return error();
		}
		return success();
	}
	
	@DeleteMapping("{id}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer id) {	
		if(!testService.deleteById(id)){
			return error();
		}
		return success();
    }	
	
	@GetMapping("{id}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer id) {	
		MTest mtest = testService.selectById(id);
		map.put("record", mtest);
		return "test/edit";
    }	
}
