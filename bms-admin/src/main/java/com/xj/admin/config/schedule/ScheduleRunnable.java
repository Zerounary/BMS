package com.xj.admin.config.schedule;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import com.feilong.core.Validator;
import com.xj.admin.util.SpringContextUtils;

/**
 * 执行定时任务
 * 
 */
public class ScheduleRunnable implements Runnable {
	
	private Logger logger = LogManager.getLogger(ScheduleRunnable.class.getName());
	
	private Object target;
	private Method method;
	private String params;
	
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;
		
		if(Validator.isNotNullOrEmpty(params)){
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		}else{
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(Validator.isNotNullOrEmpty(params)){
				method.invoke(target, params);
			}else{
				method.invoke(target);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
