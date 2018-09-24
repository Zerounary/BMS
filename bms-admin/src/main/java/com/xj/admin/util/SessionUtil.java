package com.xj.admin.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.xj.admin.base.user.entity.TbUser;


public class SessionUtil {
	
	/**
	 * 获取登录用户的userId
	 * @return
	 */
	public static Integer getloginUserId()
	{
		Subject subject = SecurityUtils.getSubject();
		TbUser sessionUser = (TbUser)subject.getSession().getAttribute("userSession");
		return sessionUser.getId();
	}
	
	/**
	 * 获取登录用户的userId
	 * @return
	 */
	public static String getloginUserAccountName()
	{
		Subject subject = SecurityUtils.getSubject();
		TbUser sessionUser = (TbUser)subject.getSession().getAttribute("userSession");
		return sessionUser.getAccountName();
	}
	
	/**
	 * 获取登录用户
	 * @return
	 */
	public static TbUser getloginUser()
	{
		Subject subject = SecurityUtils.getSubject();
		return (TbUser)subject.getSession().getAttribute("userSession");
	}
	
	
	/**
	 * 返回用户的IP地址
	 * @param request
	 * @return
	 */
	public static String getUserIP() {
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession().getHost();
	}
	
	public static Object getAttr(Object key) {
		Subject subject = SecurityUtils.getSubject();
		return subject.getSession().getAttribute(key);
	}
}
