package com.xj.admin.base.user.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.feilong.core.bean.BeanUtil;
import com.feilong.core.bean.ConvertUtil;
import com.xj.admin.base.dept.service.ITbDeptService;
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.base.role.service.ITbRoleService;
import com.xj.admin.base.user.entity.TbUser;
import com.xj.admin.base.user.service.ITbUserService;
import com.xj.admin.util.EndecryptUtils;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;
import com.xj.common.base.common.util.JsonUtil;

/**
 * <p>
 * 用户账户表  前端控制器
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
@Controller
@RequestMapping("/user/")
public class TbUserController extends BaseController{
	@Autowired
	private ITbUserService userService;
	@Autowired
	private ITbRoleService roleService;
	@Autowired
	private ITbDeptService deptService;
	
	@GetMapping("listUI")
    public String listUI() {
		return "user/list";
    }
	
	@PostMapping("list")
	@ResponseBody
    public Object list(String gridPager, HttpServletResponse response) {
		Pager pager = JsonUtil.getObjectFromJson(gridPager, Pager.class);
		Map<String, Object> parameters = null;
		if(Validator.isNullOrEmpty(pager.getParameters())){
			parameters = new HashMap<>();
		}else{
			parameters = pager.getParameters();
		}
		Integer deptId = getUserEntity().getDeptId();
		String name = "";
		if(Validator.isNotNullOrEmpty(parameters.get("deptId"))){
			deptId = Integer.parseInt(parameters.get("deptId").toString());
		}
		if(Validator.isNotNullOrEmpty(parameters.get("name"))){
			name = parameters.get("name").toString();
		}
		System.out.println("pager==="+pager.toString());
		if(pager.getIsExport()){
			System.out.println("-------------------------isExport---------------------------");
		}
		
		Page<TbUser> list = userService.selectUserList(new Page<TbUser>(pager.getNowPage(), pager.getPageSize()),name,deptId);
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@GetMapping("form")
    public String form(Map<String,Object> map) {
		makeCommon(map);
		return "user/form";
    }
	
	@GetMapping("checkAccount")
	@ResponseBody
    public boolean checkAccount(@RequestParam(required=true) String accountName) {
		List<TbUser> user = userService.selectByMap(ConvertUtil.toMap("u_account_name",(Object)accountName));
		if(!Validator.isNullOrEmpty(user)){
			return false;
		}
		return true;
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(Map<String,Object> map,TbUser user){
		if(user.getId()==null){
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			TbUser userEntity = EndecryptUtils.md5Password(user.getAccountName(), user.getPassword(), 2);
			//设置添加用户的密码和加密盐
			user.setPassword(userEntity.getPassword());
			user.setCredentialsSalt(userEntity.getCredentialsSalt());
			//设置创建者姓名
			user.setCreatorName(getUserEntity().getAccountName());
			user.setCreateTime(new Date(System.currentTimeMillis()));
			user.setUpdateTime(new Date(System.currentTimeMillis()));
			//设置锁定状态：未锁定；删除状态：未删除
			user.setLocked(0);
			user.setDeleteStatus(0);
			boolean result = userService.insertAll(user);
			if(!result)
			{
				return fail(EnumSvrResult.ERROR);
			}
		}else{
			userService.updateAll(user);
		}
		return success();
	}
	
	@DeleteMapping("{userId}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer userId) {	
		if(!userService.delUser(userId)){
			return fail(EnumSvrResult.ERROR);
		}
		return success();
    }	
	
	@GetMapping("{userId}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer userId) {	
		TbUser user = userService.selectUserRole(ConvertUtil.toMap("userId",(Object)userId));
		user.setDept(deptService.selectById(user.getDeptId()));
		map.put("user", user);
		makeCommon(map);
		return "user/edit";
    }	
	
	@GetMapping("{userId}/info")
    public String selectInfo(Map<String,Object> map,@PathVariable(required=true) Integer userId) {	
		TbUser user = userService.selectById(userId);
		map.put("user", user);
		return "user/info";
    }
	
	@GetMapping("{userId}/toRestPassword")
    public String restPassword(Map<String,Object> map,@PathVariable(required=true) Integer userId) {	
		TbUser user = userService.selectById(userId);
		map.put("user", user);
		return "user/rest";
    }	
	
	@PostMapping(value = "restPassword")
	@ResponseBody
	public AbstractBean restPassword(Map<String,Object> map,TbUser user){
		TbUser userEntity = userService.selectById(user.getId());
		TbUser userFlag = EndecryptUtils.md5Password(user.getAccountName(), user.getPassword(), 2);
		//设置添加用户的密码和加密盐
		userEntity.setPassword(userFlag.getPassword());
		userEntity.setCredentialsSalt(userFlag.getCredentialsSalt());
		userEntity.setUpdateTime(new Date(System.currentTimeMillis()));
		if(!userService.updateById(userEntity)){
			return fail(EnumSvrResult.ERROR);
		}
		return success();
	}
	
	@PostMapping("info")
	@ResponseBody
	public AbstractBean updateInfo(TbUser user){
		TbUser userEntity = getUserEntity();
		BeanUtil.copyProperties(userEntity, user, "img","userName","email","description");
		userService.updateById(userEntity);
		return success();
	}
	
	
	private void makeCommon(Map<String,Object> map){
		map.put("roles", roleService.selectRoleList(getUserEntity().getDeptId()));
	}
}
