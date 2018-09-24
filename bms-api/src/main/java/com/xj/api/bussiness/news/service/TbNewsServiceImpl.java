package com.xj.api.bussiness.news.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xj.api.bussiness.news.service.impl.ITbNewsService;
import com.xj.common.bussiness.news.entity.TbNews;
import com.xj.common.bussiness.news.mapper.TbNewsMapper;

/**
 * <p>
 * 栏目管理  服务实现类
 * </p>
 *
 * @author xj
 * @since 2017-01-05
 */
@Service
public class TbNewsServiceImpl extends ServiceImpl<TbNewsMapper, TbNews> implements ITbNewsService {
	
}
