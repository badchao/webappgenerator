<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.excel.EasyExcel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import ${basepackage}.model.${className};
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
@Api(tags={"${table.tableAlias}"})
public class ${className}Controller extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
    @Autowired
    ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@ApiOperation(value="元数据查询,返回所有搜索条件")
	@GetMapping
	public Map<String,Object> meta() {
		checkEntityPermission(getRequest(),new ${className}(),READ);
		
		//key=columnName, value=column value
		Map<String,Object> result = new HashMap<String,Object>();
		return result;
	}
	
	@ApiOperation(value="创建")
	@PostMapping
	public void create(@RequestBody ${className} ${classNameLower}) {
		checkEntityPermission(getRequest(),${classNameLower},CREATE);
		
		${classNameLower}Service.create(${classNameLower});
	}
	
	@ApiOperation(value="修改")
	@PostMapping
	public void update(@RequestBody ${className} ${classNameLower}) {
		checkEntityPermission(getRequest(),${classNameLower},UPDATE);
		
		${classNameLower}Service.updateByManual(${classNameLower});
	}
	
	@ApiOperation(value="根据ID删除")
	@PostMapping
	public void remove(@RequestBody ${className} ${classNameLower}) {
		checkEntityPermission(getRequest(),${classNameLower},DELETE);
		
		${classNameLower}Service.remove(${classNameLower});
	}

	@ApiOperation(value="根据ID查找")
	@GetMapping
	public ${className} getone(${className} ${classNameLower}) {
		checkEntityPermission(getRequest(),${classNameLower},READ);
		
		${className} result = ${classNameLower}Service.getone(${classNameLower});
		${classNameLower}Service.join(result);
		
		return result;
	}
	
	@ApiOperation(value="分页查询")
	@GetMapping
	public Page<${className}> query(${className}Query query){
		checkEntityPermission(getRequest(),new ${className}(),READ);
		
		Page<${className}> result = ${classNameLower}Service.query(query);
		result.forEach(${classNameLower}Service::join);
		
		return result;
	}
	
	@ApiOperation(value="导出下载")
	@GetMapping
	public void download(${className}Query query)  {
		Page<${className}> result = query(query);
		writeExcel2Response(getResponse(),result.getItemList(),${className}.class);
	}
	
}

