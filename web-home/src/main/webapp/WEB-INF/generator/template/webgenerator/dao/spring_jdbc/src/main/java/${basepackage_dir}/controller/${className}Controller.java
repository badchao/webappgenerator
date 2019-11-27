<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ${basepackage}.model.${className};
import ${basepackage}.query.${className}Query;
import ${basepackage}.service.${className}Service;
import com.github.rapid.common.util.page.Page;

/**
 * [${table.tableAlias}] 的前端用户  Controller
 * 
<#include "/java_description.include">
 */

@Controller
@RequestMapping("/admin/${classNameLower}")
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@PostMapping
	public void create(@RequestBody ${className} ${classNameLower},HttpServletRequest request) {
		${classNameLower}Service.checkPermission(getLoginUserId(request),${classNameLower},"w");
		
		${classNameLower}Service.create(${classNameLower});
	}
	
	@PostMapping
	public void update(@RequestBody ${className} ${classNameLower},HttpServletRequest request) {
		${classNameLower}Service.checkPermission(getLoginUserId(request),${classNameLower},"w");
		
		Integer id = ${classNameLower}.getId();

		//不可以让客户端可以更新所有属性
		${className} fromDb = ${classNameLower}Service.getById(id);
		BeanUtils.copyProperties(${classNameLower}, fromDb,"createTime"); //ignore some copy property
		
		${classNameLower}Service.update(fromDb);
	}
	
	@PostMapping
	public void removeById(${className} ${classNameLower},<@generateArguments table.pkColumns/>,HttpServletRequest request) {
		${classNameLower}Service.checkPermission(getLoginUserId(request),${classNameLower},"w");
		
		${classNameLower}Service.removeById(<@generatePassingParameters table.pkColumns/>);
	}

	@GetMapping
	public ResponseEntity<?> getById(boolean join,<@generateArguments table.pkColumns/>,${className} ${classNameLower},HttpServletRequest request) {
		${classNameLower}Service.checkPermission(getLoginUserId(request),${classNameLower},"r");
		
		${className} result = ${classNameLower}Service.getById(<@generatePassingParameters table.pkColumns/>);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping
	public ResponseEntity<?> findPage(boolean join,${className}Query query,${className} ${classNameLower},HttpServletRequest request){
		${classNameLower}Service.checkPermission(getLoginUserId(request),${classNameLower},"r");
		
		Page<${className}> page = ${classNameLower}Service.findPage(query);
		return ResponseEntity.ok(page);
	}
	
}
