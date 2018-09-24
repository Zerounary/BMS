package com.xj.admin.base.loginfo.web;

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
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.base.loginfo.entity.TbLogInfo;
import com.xj.admin.base.loginfo.mapper.TbLogInfoMapper;
import com.xj.admin.base.loginfo.service.ITbLogInfoService;
import com.xj.admin.util.JsonUtil;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author xj
 * @since 2017-05-23
 */
@Controller
@RequestMapping("/loginfo/")
public class TbLogInfoController extends BaseController {

	@Autowired
	private ITbLogInfoService loginfoService;
	
	@Autowired
	private TbLogInfoMapper loginfoMapper;
	
	@GetMapping("listUI")
    public String listUI() {
		return "loginfo/list";
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
		Page<TbLogInfo> list = loginfoService.selectPage(new Page<TbLogInfo>(pager.getNowPage(), pager.getPageSize()), Condition.create().allEq(parameters).orderBy("operate_time", false));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@GetMapping("{id}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer id) {	
		TbLogInfo tbloginfo = loginfoService.selectById(id);
		map.put("record", tbloginfo);
		return "loginfo/edit";
    }	
	
	@DeleteMapping("deleteBatch")
	@ResponseBody
    public AbstractBean delete() {	
		loginfoMapper.deleteByMap(null);
		return success();
    }	
}
