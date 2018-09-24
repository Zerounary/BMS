package com.xj.admin.base.schedule.service.impl;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xj.admin.base.schedule.entity.TbSchedule;
import com.xj.admin.base.schedule.mapper.TbScheduleMapper;
import com.xj.admin.base.schedule.service.ITbScheduleService;
import com.xj.admin.config.support.ScheduleSupport;

/**
 * <p>
 * 任务调度 服务实现类
 * </p>
 *
 * @author xj
 * @since 2017-05-15
 */
@Service
public class TbScheduleServiceImpl extends ServiceImpl<TbScheduleMapper, TbSchedule> implements ITbScheduleService {
	
	@Autowired
	private TbScheduleMapper scheduleMapper;
	
	@Autowired
	private ScheduleSupport scheduleSupport;
	
	/**
	 * 更改任务状态
	 * 
	 * @param scheduleJob
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public int changeStatus(TbSchedule schedule,String status) {
		try {
			if (status.equals("0")) {
				scheduleSupport.delete(schedule);
			} else if (status.equals("1")) {
				if(schedule.getStatus().equals("2")){
					scheduleSupport.resume(schedule);
				}else{
					scheduleSupport.add(schedule);
				}
			}else if(status.equals("2")){
				scheduleSupport.pause(schedule);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		schedule.setStatus(status.toString());
		return scheduleMapper.updateById(schedule);
	}

}
