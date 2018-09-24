package com.xj.admin.base.schedule.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
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
import com.xj.admin.base.schedule.entity.TbSchedule;
import com.xj.admin.base.schedule.service.ITbScheduleService;
import com.xj.admin.config.support.ScheduleSupport;
import com.xj.admin.util.JsonUtil;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;

/**
 * <p>
 * 任务调度  前端控制器
 * </p>
 *
 * @author xj
 * @since 2017-05-15
 */
@Controller
@RequestMapping("/schedule/")

public class TbScheduleController extends BaseController {

	@Autowired
	private ITbScheduleService scheduleService;
	
	@Autowired
	private ScheduleSupport sheduleSupport;
	
	@GetMapping("listUI")
    public String listUI() {
		return "schedule/list";
    }
	
	@GetMapping("form")
    public String form(Map<String,Object> map) {
		return "schedule/form";
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
		@SuppressWarnings("unchecked")
		Page<TbSchedule> list = scheduleService.selectPage(new Page<TbSchedule>(pager.getNowPage(), pager.getPageSize()), Condition.create().allEq(parameters));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(TbSchedule tbschedule){
		if(!CronExpression.isValidExpression(tbschedule.getCron())){
			return fail(EnumSvrResult.ERROR_SCHEDULE_CRON);
		}
		if(Validator.isNotNullOrEmpty(tbschedule.getIsConcurrent())){
			tbschedule.setIsConcurrent("1");
		}else{
			tbschedule.setIsConcurrent("2");
		} 
		if(Validator.isNotNullOrEmpty(tbschedule.getId())){
			tbschedule.setUpdateTime(new Date());
			tbschedule.setUpdateUser(getUserEntity().getAccountName());
			scheduleService.updateById(tbschedule);
		}else{
			tbschedule.setStatus("0");
			tbschedule.setCreateTime(new Date());
			tbschedule.setCreateUser(getUserEntity().getAccountName());
			scheduleService.insert(tbschedule);
		}
		return success();
	}
	
	@DeleteMapping("{id}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer id) {	
		if(!scheduleService.deleteById(id)){
			return error();
		}else{
			try {
				TbSchedule tbschedule = scheduleService.selectById(id);
				sheduleSupport.delete(tbschedule);
			} catch (SchedulerException e) {
				return error();
			}
		}
		return success();
    }	
	
	@GetMapping("{id}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer id) {	
		TbSchedule tbschedule = scheduleService.selectById(id);
		map.put("record", tbschedule);
		return "schedule/edit";
    }	
	
	@GetMapping("{id}/run")
	@ResponseBody
	public AbstractBean run(@PathVariable Integer id) {
		try {
			TbSchedule tbschedule = scheduleService.selectById(id);
			sheduleSupport.runAJobNow(tbschedule);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return error();
		}
		return success();
	}
	
	@GetMapping("{id}/change/{status}")
	@ResponseBody
	public AbstractBean change(@PathVariable Integer id,@PathVariable String status) {
		TbSchedule tbschedule = scheduleService.selectById(id);
		scheduleService.changeStatus(tbschedule,status);
		return success();
	}
}
