package com.xj.admin.base.loginlog.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.base.loginlog.entity.TbLoginInfo;
import com.xj.admin.base.loginlog.mapper.TbLoginInfoMapper;
import com.xj.admin.base.loginlog.service.ITbLoginInfoService;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.util.JsonUtil;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author xj
 * @since 2017-01-04
 */
@Controller
@RequestMapping("/loginlog/")

public class TbLoginInfoController extends BaseController {

	@Autowired
	private ITbLoginInfoService loginlogService;
	
	@Autowired
	private TbLoginInfoMapper loginlogMapper;
	
	@RequestMapping("listUI")
    public String listUI() {
		return "loginlog/list";
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
		Page<TbLoginInfo> list = loginlogService.selectPage(new Page<TbLoginInfo>(pager.getNowPage(), pager.getPageSize()), Condition.create().allEq(parameters).orderBy("login_time", false));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@DeleteMapping("deleteBatch")
	@ResponseBody
    public AbstractBean delete() {	
		loginlogMapper.deleteByMap(null);
		return success();
    }	

}
