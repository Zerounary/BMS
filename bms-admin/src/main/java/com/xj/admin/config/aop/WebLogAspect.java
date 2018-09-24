package com.xj.admin.config.aop;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.feilong.core.Validator;
import com.feilong.core.date.DateExtensionUtil;
import com.xj.admin.base.loginfo.entity.TbLogInfo;
import com.xj.admin.base.loginfo.service.ITbLogInfoService;
import com.xj.admin.base.user.entity.TbUser;
import com.xj.admin.util.SessionUtil;
import com.xj.admin.util.ShiroUtils;

@Aspect
@Component
public class WebLogAspect {
	private Logger logger = LogManager.getLogger(WebLogAspect.class.getName());

	@Resource
	private ITbLogInfoService logInfoService;

	@Around("execution(public * com.xj.admin.*.*.web.*.*(..))")
	public Object doWebLog(ProceedingJoinPoint joinPoint) throws Throwable {
		Date beginTime = new Date();
		Long id = 0L;
		Object returnVal;
		 String methodName = joinPoint.getSignature().getName();
        if (methodName.equals("login") || methodName.equals("doLogin")) {
            return joinPoint.proceed();
        }
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			String methodToUse = joinPoint.getSignature().getDeclaringTypeName() + "."
					+ joinPoint.getSignature().getName();
			logger.info("URL : " + request.getRequestURL().toString());
			logger.info("HTTP_METHOD : " + request.getMethod());
			logger.info("IP : " + request.getRemoteAddr());
			logger.info("CLASS_METHOD : " + methodToUse);
			logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
			id = doWebLog(request,null);
			returnVal = joinPoint.proceed();
			updateWebLog(id, "成功", "操作成功",DateExtensionUtil.formatDuration(beginTime));
		} catch (Exception e) {
			updateWebLog(id, "失败", e.getMessage(),DateExtensionUtil.formatDuration(beginTime));
			throw new RuntimeException(e);
		}
		return returnVal;
	}

	private Long doWebLog(HttpServletRequest request, Exception ex) {
		TbLogInfo logInfo = new TbLogInfo();
		TbUser user = SessionUtil.getloginUser();
		if(Validator.isNotNullOrEmpty(user)){
			logInfo.setAccountName(user.getAccountName());
			logInfo.setUserId(user.getId());
		}
		logInfo.setMethodArgs(makeParams(request.getParameterMap()));
		logInfo.setMethod(request.getMethod());
		logInfo.setOperateTime(new Date());
		logInfo.setOperateIp(ShiroUtils.getRemoteAddr(request));
		logInfo.setRequestUri(request.getRequestURI());
		logInfo.setUserAgent(request.getHeader("user-agent"));
		if (ex != null)
			logInfo.setException(ex.getMessage());
		logInfoService.insert(logInfo);
		return logInfo.getId();
	}

	private String makeParams(Map<String, String[]> paramMap) {
		if (paramMap == null) {
			return "";
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(paramValue);
		}
		return params.toString();
	}

	private void updateWebLog(Long id, String status, String remark,String times) {
		TbLogInfo logInfo = new TbLogInfo();
		logInfo.setId(id);
		logInfo.setException(remark);
		logInfo.setStatus(status);
		logInfo.setOperateTime(new Date());
		logInfo.setTimes(times);
		logInfoService.updateById(logInfo);
	}

}
