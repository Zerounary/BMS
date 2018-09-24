package com.xj.admin.config.schedule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xj.admin.base.schedule.entity.TbSchedule;

/**
 * 计划任务执行处 无状态
 * 
 * @author cc
 */
public class QuartzJobFactory implements Job {
	
	private static Logger logger = LogManager.getLogger(QuartzJobFactory.class.getName());
	
	private ExecutorService service = Executors.newSingleThreadExecutor(); 

	public void execute(JobExecutionContext context) throws JobExecutionException {
		TbSchedule schedule = (TbSchedule) context.getMergedJobDataMap().get("schedule");
		try {
			long startTime = System.currentTimeMillis();
			ScheduleRunnable task = new ScheduleRunnable(schedule.getBeanName(), schedule.getMethodName(), "");
			Future<?> future = service.submit(task);
			future.get();
			long times = System.currentTimeMillis() - startTime;
			logger.info("任务执行完毕，任务名称：" + schedule.getJobName() + "  总共耗时：" + times + "毫秒");
		} catch (Exception e) {
			logger.error("任务执行失败，任务名称：" + schedule.getJobName(), e);
		}
	}
}