package com.xj.admin.base.upload.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.util.ImageUploadUtil;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;

/**
 * <p>
 * 用户账户表 前端控制器
 * </p>
 *
 * @author xj
 * @since 2016-12-20
 */
@Controller
@RequestMapping("/upload/")
public class UploadController extends BaseController{
	private Logger logger = LogManager.getLogger(UploadController.class.getName());

	@RequestMapping("ckUploadImg")
	public void ckUpdateImg(HttpServletRequest request,HttpServletResponse response){
			String DirectoryName = "upload/ck/";
		try{	
			ImageUploadUtil.ckeditor(request, response, DirectoryName);
			
		}catch(Exception e){
			logger.error("CK上傳失敗：{}",e.getMessage());
		}
		
	}
	
	@RequestMapping("uploadImg")
	@ResponseBody
	public AbstractBean updateImg(HttpServletRequest request){
			String DirectoryName = "upload/other/";
		try{	
			String fileName = ImageUploadUtil.upload(request, DirectoryName);
			return json(DirectoryName+fileName);
		}catch(Exception e){
			logger.error("上傳失敗：{}",e.getMessage());
			return fail(EnumSvrResult.ERROR_UPLOAD_IMG);
		}
	}
}
