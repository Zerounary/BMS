package com.xj.api.bussiness.news.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xj.api.base.web.BaseController;
import com.xj.api.bussiness.news.service.impl.ITbNewsService;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.bussiness.news.entity.TbNews;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("news")
public class NewsController extends BaseController{
	
	@Autowired
	private ITbNewsService newsService;
	
	@GetMapping
	@ApiOperation(value = "新聞列表", httpMethod = "GET", notes = "新聞列表")
	public AbstractBean newsList(@RequestParam(required=false,defaultValue="0") Integer pageNum,@RequestParam(required=false,defaultValue="10") Integer pageSize){
		return json(newsService.selectMapsPage(new Page<TbNews>(pageNum, pageSize), new EntityWrapper<TbNews>()));
	}
	
	@GetMapping("/{id}.auth")
	@ApiOperation(value = "新聞詳情", httpMethod = "GET", notes = "新聞詳情")
	public AbstractBean newsContent(@PathVariable Integer id){
		return json(newsService.selectById(id));
	}
}