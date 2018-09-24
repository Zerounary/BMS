package com.xj.admin.base.dict.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.feilong.core.Validator;
import com.xj.admin.base.dict.entity.TbDict;
import com.xj.admin.base.dict.service.ITbDictService;
import com.xj.admin.base.index.web.BaseController;
import com.xj.admin.util.JsonUtil;
import com.xj.admin.util.dtgrid.model.Pager;
import com.xj.common.base.common.bean.AbstractBean;
import com.xj.common.base.common.exception.EnumSvrResult;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author xj
 * @since 2017-06-01
 */
@Controller
@RequestMapping("/dict/")
public class TbDictController extends BaseController {

	@Autowired
	private ITbDictService dictService;
	
	@GetMapping("listUI")
    public String listUI() {
		return "dict/list";
    }
	
	@GetMapping("form")
    public String form(Map<String,Object> map,Integer pid,String code) {
		map.put("pid", pid==null?0:pid);
		map.put("code", code==null?"":code);
		return "dict/form";
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("list")
	@ResponseBody
    public Object list(String gridPager) {
		Pager pager = JsonUtil.getObjectFromJson(gridPager, Pager.class);
		Map<String, Object> parameters = null;
		if(Validator.isNullOrEmpty(pager.getParameters())){
			parameters = new HashMap<>();
			parameters.put("pid", 0);
		}else{
			parameters = pager.getParameters();
			if(Validator.isNullOrEmpty(parameters.get("pid"))){
				parameters.put("pid", 0);
			}
		}
		
		Page<TbDict> list = dictService.selectDictPage(new Page<TbDict>(pager.getNowPage(), pager.getPageSize()), Condition.create().allEq(parameters));
		makeGridResult(parameters, pager, list);
		return parameters;
    }
	
	@PostMapping("save")
	@ResponseBody
	public AbstractBean add(TbDict tbdict){
		if(Validator.isNotNullOrEmpty(tbdict.getId())){
			Integer oldVersion = tbdict.getVersion();
			if(!dictService.updateById(tbdict)){
				TbDict record = dictService.selectById(tbdict.getId());
				if(Validator.isNullOrEmpty(record)){
					return fail(EnumSvrResult.ERROR_UPDATE_RECORD_DEFIND);
				}else{
					if(!oldVersion.equals(record.getVersion())){
						return fail(EnumSvrResult.ERROR_UPDATE_RECORD_VERSION);
					}
					return error();
				}
			}
		}else{
			dictService.insert(tbdict);
		}
		return success();
	}
	
	@DeleteMapping("{id}/delete")
	@ResponseBody
    public AbstractBean delete(@PathVariable(required=true) Integer id) {	
		if(!dictService.deleteById(id)){
			return error();
		}
		return success();
    }	
	
	@GetMapping("{id}/select")
    public String select(Map<String,Object> map,@PathVariable(required=true) Integer id) {	
		TbDict tbdict = dictService.selectById(id);
		map.put("record", tbdict);
		return "dict/edit";
    }	
	
	/**
	 * @param codes
	 * @return
	 */
	@GetMapping
	@ResponseBody
    public AbstractBean select(@RequestParam String codes) {	
		Map<String,Object> result = new HashMap<>();
		Map<Integer,String> codeDicts = new HashMap<>();
		for(String code : codes.split(",")){
			codeDicts.clear();
			result.put(code, dictService.selectDictListByCode(code));
		}
		return json(result);
    }	
	
}
