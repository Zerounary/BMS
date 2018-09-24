package com.xj.api.bussiness.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.api.base.web.BaseController;
import com.xj.api.config.jwt.JwtHelper;
import com.xj.common.base.common.bean.AbstractBean;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("user")
public class UserController extends BaseController{
	
	@PostMapping("login")
	@ApiOperation(value = "接口登录demo", httpMethod = "POST", notes = "接口登录demo")
	public AbstractBean newsList(@RequestParam String userName,@RequestParam String password){
		if(userName.equals("zhangsan")&& password.equals("123456")){
			//PWDHelper.md532("password")  md5 32位加密工具
			String token = JwtHelper.createJWT(userName, "1", 30*60*1000);  //用户用户名密码验证成功后创建token, 有效期为30*60*1000 毫秒
			return json(token);
		}else{
			return fail("账号或密码错误！", "-1");
		}
	}
}