<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.controller;

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

import io.swagger.annotations.ApiOperation;
import ${basepackage}.query.${className}Query;
import ${basepackage}.service.${className}Service;
import com.github.rapid.common.util.page.Page;

/**
 * [${table.tableAlias}] 用户前台  Controller
 * 
<#include "/java_description.include">
 */

@RestController
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
    @Autowired
    private ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@ApiOperation(value="创建")
	@PostMapping
	public void create(${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},WRITE);
		
		${classNameLower}Service.create(${classNameLower});
	}
	
	@ApiOperation(value="修改")
	@PostMapping
	public void update(${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},WRITE);
		
		${classNameLower}Service.updateByManual(${classNameLower});
	}
	
	@ApiOperation(value="删除")
	@PostMapping
	public void removeById(${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},WRITE);
		
		${classNameLower}Service.removeById(${classNameLower});
	}

	@ApiOperation(value="ID查找")
	@GetMapping
	public ${className} getById(boolean join,${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},READ);
		
		${className} result = ${classNameLower}Service.getById(${classNameLower});
		if(join) ${classNameLower}Service.join(result);
		
		return result;
	}
	
	@ApiOperation(value="分页查询")
	@GetMapping
	public Page<${className}> findPage(boolean join,${className}Query query,${className} ${classNameLower},HttpServletRequest request){
		checkEntityPermission(request,${classNameLower},READ);
		
		Page<${className}> result = ${classNameLower}Service.findPage(query);
		if(join) result.forEach(${classNameLower}Service::join);
		
		return result;
	}
	
}

