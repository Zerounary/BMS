package com.xj.admin.base.schedule.service;

import com.baomidou.mybatisplus.service.IService;
import com.xj.admin.base.schedule.entity.TbSchedule;

/**
 * <p>
 * 任务调度 服务类
 * </p>
 *
 * @author xj
 * @since 2017-05-15
 */
public interface ITbScheduleService extends IService<TbSchedule> {
	
	 int changeStatus(TbSchedule schedule,String status);

}
