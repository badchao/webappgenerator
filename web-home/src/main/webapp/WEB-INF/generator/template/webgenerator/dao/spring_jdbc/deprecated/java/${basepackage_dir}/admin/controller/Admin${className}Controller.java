<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.admin.controller;

import javax.servlet.http.HttpServletRequest;

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

import ${basepackage}.model.${className};
import ${basepackage}.query.${className}Query;
import ${basepackage}.service.${className}Service;
import com.github.rapid.common.util.page.Page;

import io.swagger.annotations.ApiOperation;

/**
 * [${table.tableAlias}] 管理后台  Controller
 * 
<#include "/java_description.include">
 */

@RestController
@RequestMapping("/admin/${classNameLower}")
public class Admin${className}Controller extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(Admin${className}Controller.class);
	
    @Autowired
    private ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@ApiOperation(value="创建")
	@PostMapping
	public void create(${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,WRITE);
		
		${classNameLower}Service.create(${classNameLower});
	}
	
	@ApiOperation(value="修改")
	@PostMapping
	public void update(${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,WRITE);
		
		${classNameLower}Service.updateByManual(${classNameLower});
	}
	
	@ApiOperation(value="删除")
	@PostMapping
	public void removeById(${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,WRITE);
		
		${classNameLower}Service.removeById(${classNameLower});
	}

	@ApiOperation(value="ID查找")
	@GetMapping
	public ${className} getById(boolean join,${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,READ);
		
		${className} result = ${classNameLower}Service.getById(${classNameLower});
		if(join) {
			${classNameLower}Service.join(result);
		}
		return result;
	}
	
	@ApiOperation(value="分页查询")
	@GetMapping
	public Page<${className}> query(boolean join,${className}Query query,HttpServletRequest request){
		checkActionPermission(request,${className}.class,READ);
		
		Page<${className}> page = ${classNameLower}Service.query(query);
		if(join) {
			page.forEach(${classNameLower}Service::join);
		}
		return page;
	}
	
}

