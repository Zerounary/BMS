package com.xj.admin.base.index.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.feilong.core.Validator;
import com.xj.admin.base.resource.entity.TbResource;
import com.xj.admin.base.resource.service.ITbResourceService;
import com.xj.admin.base.user.entity.TbUser;
import com.xj.admin.util.TreeUtil;

/**
 * <p>
 * 用户账户表 前端控制器
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
@Controller
public class IndexController {
	private Logger logger = LogManager.getLogger(IndexController.class.getName());
	
	@Resource
	private ITbResourceService resourceService;
	
	@RequestMapping({"/","/index" })
	public String index(Map<String, Object> map) {
		TbUser userEntity = (TbUser)SecurityUtils.getSubject().getPrincipal();
		List<TbResource> treeMenuList = null;
		if(userEntity.getRole().getKey().equals("administrator")){
			treeMenuList = new TreeUtil().treeMenuList(resourceService.selectByMap(null), 0);
		}else{
			treeMenuList = resourceService.findResourcesMenuByUserId(userEntity.getId());
		}
		map.put("menus", treeMenuList);
		map.put("user", userEntity);
		return "index";
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
    public String login(HttpServletResponse response,ServletRequest request,@RequestHeader HttpHeaders header,Map<String, Object> map,String msg){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return "redirect:index";
		}
		if(Validator.isNotNullOrEmpty(header.get("X-Requested-With"))){
			response.setHeader("sessionstatus", "timeout");
		}
       return "login";
    }
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(String username,String password,String rememberMe, Map<String, Object> map) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>{},{},{}",username,password,rememberMe);
		String msg = "";
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe==null?false:true);
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("account", username);
        try{
        	subject.login(token);
        	logger.info("{}登陆成功!",username);
        	  subject.getSession().removeAttribute("msg");
        	return "redirect:/index";
        }catch(UnknownAccountException e){
			msg = "账户不存在！";
        }catch(IncorrectCredentialsException e){
			msg = "密码错误！";
        }catch (LockedAccountException e) {
        	msg = "您的账户已被锁定,请与管理员联系！";
        }catch(ExcessiveAttemptsException e){
        	msg = "登录失败次数过多,请稍后再试！";
        }catch(Exception e){
        	 msg="系统发生错误，请联系管理员！";
        }
		map.put("msg", msg);
		// 此方法不处理登录成功,由shiro进行处理.
		logger.info("{}登陆失败，error={}!",username,msg);
		subject.getSession().setAttribute("msg", msg);
		return "login";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		// 注销登录
		SecurityUtils.getSubject().logout();
		return "redirect:login";
	}
}
