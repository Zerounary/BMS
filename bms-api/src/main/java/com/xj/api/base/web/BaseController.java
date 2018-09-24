package com.xj.api.base.web;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feilong.core.DatePattern;
import com.feilong.core.date.DateUtil;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;


public abstract class BaseController {
	
	/**
	 * 成功,返回状态
	 * @return
	 */
	public AbstractBean success() {
		return new AbstractBean();
	}
	
	/**
	 * 失败,返回状态及原因
	 * @param message 消息
	 * @return
	 */
	public AbstractBean fail(EnumSvrResult result) {
		AbstractBean bean = new AbstractBean();
		bean.setMessage(result.getName());
		bean.setStatus(result.getVal());
		return bean;
	}
	
	public AbstractBean fail(String message,String status) {
		AbstractBean bean = new AbstractBean();
		bean.setMessage(message);
		bean.setStatus(status);
		return bean;
	}
	
	public AbstractBean error() {
		AbstractBean bean = new AbstractBean();
		bean.setMessage(EnumSvrResult.ERROR.getName());
		bean.setStatus(EnumSvrResult.ERROR.getVal());
		return bean;
	}
	
	
	/**
	 * 成功，返回json数据
	 * @param message 消息
	 * @return
	 */
	public AbstractBean json(Object data) {
		AbstractBean bean = new AbstractBean();
		bean.setData(data);
		return bean;
	}
	
	public String toJson(Object object) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	public <T> T getJson(String json,Class<T> valueType) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , false);
		return mapper.readValue(json,valueType);
	}
	
	public String getFolder(){
		return "product/"+DateUtil.toString(new Date(), DatePattern.yyyyMMdd)+"/";
	}
	
}