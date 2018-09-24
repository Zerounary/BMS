package com.xj.admin.config.support;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.xj.admin.base.schedule.entity.TbSchedule;
import com.xj.admin.base.schedule.mapper.TbScheduleMapper;
import com.xj.admin.config.schedule.QuartzJobFactory;
import com.xj.admin.config.schedule.QuartzJobFactoryDisallowConcurrentExecution;

@Component
public class ScheduleSupport {
	
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private TbScheduleMapper scheduleMapper;
	
	/**
	 * 初始化，动态定时器
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		// 这里获取任务信息数据
		List<TbSchedule> schedules = scheduleMapper.selectByMap(ConvertUtil.toMap("status",(Object)1));
		for (TbSchedule schedule : schedules) {
			add(schedule);
		}
	}
	/**
	 * 添加任务
	 * 
	 * @param schedule
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public void add(TbSchedule schedule) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(schedule.getJobName(), schedule.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (Validator.isNullOrEmpty(trigger)) {
			Class<?> clazz =schedule.getIsConcurrent().equals("1") ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) clazz).withIdentity(schedule.getJobName(), schedule.getJobGroup()).build();
			jobDetail.getJobDataMap().put("schedule", schedule);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCron());
			trigger = TriggerBuilder.newTrigger().withIdentity(schedule.getJobName(), schedule.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCron());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 暂停一个job
	 * 
	 * @param schedule
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void pause(TbSchedule schedule) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(schedule.getJobName(), schedule.getJobGroup());
		scheduler.pauseJob(jobKey);
		scheduleMapper.updateById(schedule);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param schedule
	 * @throws SchedulerException
	 */
	@Transactional(readOnly = false)
	public void resume(TbSchedule schedule) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(schedule.getJobName(), schedule.getJobGroup());
		scheduler.resumeJob(jobKey);
		scheduleMapper.updateById(schedule);
	}

	/**
	 * 删除一个job
	 * 
	 * @param schedule
	 * @throws SchedulerException
	 */
	public void delete(TbSchedule schedule) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(schedule.getJobName(), schedule.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即执行job
	 * 
	 * @param schedule
	 * @throws SchedulerException
	 */
	public void runAJobNow(TbSchedule schedule) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(schedule.getJobName(), schedule.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param schedule
	 * @throws SchedulerException
	 */
	public void updateJobCron(TbSchedule schedule) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(schedule.getJobName(), schedule.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedule.getCron());
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
	}
}
