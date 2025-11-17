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
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import ${basepackage}.entity.${className};
import ${basepackage}.query.${className}Query;
import ${basepackage}.service.${className}Service;

import java.util.Map;
import java.util.HashMap;

import com.znyx.core.utils.ZnyxUtil;


/**
 * [${table.tableAlias}] Controller
 * 
<#include "/java_description.include">
 */

@RestController
@RequestMapping("/${classNameLower}")
@Api(tags = "${className}-${table.tableAlias}")
public class ${className}Controller  {
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
    @Autowired
    ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@ApiOperation("元数据查询,得到所有相关枚举等元数据,返回所有搜索条件")
	@GetMapping("meta")
	public ResultBean<Map<String,Object>> meta() {
		
		//key=columnName, value=column value
		Map<String,Object> result = new HashMap<String,Object>();
		return ResultBean.success(result);
	}
	
	@ApiOperation("创建")
	@PostMapping("create")
	public ResultBean<${className}> create(@RequestBody ${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        ${classNameLower}.setCreateUserId(userId);
		${classNameLower}Service.insert(${classNameLower});
		
		return ResultBean.success(${classNameLower});
	}
	
	@ApiOperation("修改")
	@PostMapping("update")
	public ResultBean<${className}> update(@RequestBody ${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        ${classNameLower}.setUpdateUserId(userId);
		${classNameLower}Service.updateById(${classNameLower});
		
		return ResultBean.success(${classNameLower});
	}
	
	@ApiOperation("根据ID删除")
	@PostMapping("remove")
	public ResultBean<Boolean> remove(@RequestBody ${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        ${table.pkColumn.javaType} id = ${classNameLower}.id();
		${classNameLower}Service.deleteById(id);
		
		return ResultBean.success(true);
	}

	@ApiOperation("根据ID查找")
	@GetMapping("getone")
	public ResultBean<${className}> getone(${className} ${classNameLower}) {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        ${table.pkColumn.javaType} id = ${classNameLower}.id();
		${className} result = ${classNameLower}Service.unique(id);
		${classNameLower}Service.join(result);
		
		return ResultBean.success(result);
	}
	
	@ApiOperation("分页查询")
	@GetMapping("query")
	public ResultBean query(${className}Query query){
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        query.setRootShopId(rootShopId);
        
		PageQuery<${className}> page = ${classNameLower}Service.query(query);
		return ResultBean.returnList(page);
	}
	
	@ApiOperation("导出下载")
	@GetMapping("download")
	public void download(${className}Query query)  {
        String userId = ZnyxUtil.getUserId();
        String shopId = ZnyxUtil.getShopId();
        String rootShopId = ZnyxUtil.getRootShopId();
        
        query.setRootShopId(rootShopId);
        query.setPageSize(1000);
        
        PageQuery<${className}> page = ${classNameLower}Service.query(query);
//		writeExcel2Response(getResponse(),result.getItemList(),${className}.class);
	}
	
}

