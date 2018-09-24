package com.xj.admin.config.aop;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.feilong.core.Validator;
import com.xj.admin.base.loginlog.entity.TbLoginInfo;
import com.xj.admin.base.loginlog.service.ITbLoginInfoService;
import com.xj.admin.util.SessionUtil;

@Aspect
@Component
public class LoginAop {

    private Logger logger = LoggerFactory.getLogger(LoginAop.class);

    @Resource
    private ITbLoginInfoService loginLogService;

    @Pointcut(value="execution(* com.xj.admin.base.index.web.IndexController.doLogin(..))))")
    private void loginLog() {
    }

    @Before("loginLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("loginLog()")
    public void doAfter() {
    	String account = (String)SessionUtil.getAttr("account");
    	String msg = (String)SessionUtil.getAttr("msg");
    	 if(Validator.isNotNullOrEmpty(msg)){
    		 this.writeLoginLog("登录失败",msg,account);
         }else{
        	 this.writeLoginLog("登录成功",null,account);
         }
    }

    private void writeLoginLog(String status,String msg,String account) {
        TbLoginInfo loginLog = new TbLoginInfo();
        loginLog.setStatus(status);
        loginLog.setAccountName(account);
        loginLog.setLoginTime(new Date());
        loginLog.setIp(SessionUtil.getUserIP());
        loginLog.setRemark(msg);
        loginLogService.insert(loginLog);
    }
}
