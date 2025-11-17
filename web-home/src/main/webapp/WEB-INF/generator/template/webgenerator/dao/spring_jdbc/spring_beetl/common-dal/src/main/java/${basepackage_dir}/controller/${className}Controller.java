<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import ${basepackage}.entity.${className};
import ${basepackage}.query.${className}Query;
import ${basepackage}.service.${className}Service;

import com.github.rapid.common.util.page.Page;

import java.util.Map;
import java.util.HashMap;

/**
 * [${table.tableAlias}] Controller
 * 
<#include "/java_description.include">
 */

@RestController
@RequestMapping("/${classNameLower}")
@Api(tags = "${className}-${table.tableAlias}")
public class ${className}Controller extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
    @Autowired
    ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@ApiOperation(summary = "元数据查询,得到所有相关枚举等元数据,返回所有搜索条件")
	@GetMapping("meta")
	public Map<String,Object> meta() {
		
		//key=columnName, value=column value
		Map<String,Object> result = new HashMap<String,Object>();
		return result;
	}
	
	@ApiOperation(summary = "创建")
	@PostMapping("create")
	public void create(@RequestBody ${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
		${classNameLower}Service.insert(${classNameLower});
	}
	
	@ApiOperation(summary = "修改")
	@PostMapping("update")
	public void update(@RequestBody ${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
		${classNameLower}Service.updateById(${classNameLower});
	}
	
	@ApiOperation(summary = "根据ID删除")
	@PostMapping("remove")
	public void remove(@RequestBody ${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        ${table.pkColumn.javaType} id = ${classNameLower}.id();
		${classNameLower}Service.deleteById(id);
	}

	@ApiOperation(summary = "根据ID查找")
	@GetMapping("getone")
	public ${className} getone(${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        ${table.pkColumn.javaType} id = ${classNameLower}.id();
		${className} result = ${classNameLower}Service.unique(id);
		${classNameLower}Service.join(result);
		
		return result;
	}
	
	@ApiOperation(summary = "分页查询")
	@GetMapping("query")
	public Page<${className}> query(${className}Query query){
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        query.setRootShopId(rootShopId);
        
		Page<${className}> result = ${classNameLower}Service.query(query);
		result.forEach(${classNameLower}Service::join);
		
		return result;
	}
	
	@ApiOperation(summary = "导出下载")
	@GetMapping("download")
	public void download(${className}Query query)  {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
		Page<${className}> result = query(query);
		writeExcel2Response(getResponse(),result.getItemList(),${className}.class);
	}
	
}

