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
 * [${table.tableAlias}] 管理后台  Controller
 * 
<#include "/java_description.include">
 */

@Controller
@RequestMapping("/admin/${classNameLower}")
public class Admin${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@PostMapping
	public ResponseEntity<?> create(${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"w");
		
		${classNameLower}Service.create(${classNameLower});
		return ResponseEntity.ok("success");
	}
	
	@PostMapping
	public ResponseEntity<?> update(${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"w");
		
		${classNameLower}Service.updateByManual(${classNameLower});
		return ResponseEntity.ok("success");
	}
	
	@PostMapping
	public ResponseEntity<?> removeById(${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"w");
		
		${classNameLower}Service.removeById(${classNameLower});
		return ResponseEntity.ok("success");
	}

	@GetMapping
	public ResponseEntity<?> getById(boolean join,${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"r");
		
		${className} result = ${classNameLower}Service.getById(${classNameLower});
		return ResponseEntity.ok(result);
	}
	
	@GetMapping
	public ResponseEntity<?> findPage(boolean join,${className}Query query,HttpServletRequest request){
		checkActionPermission(request,${className}.class,"r");
		
		Page<${className}> page = ${classNameLower}Service.findPage(query);
		return ResponseEntity.ok(page);
	}
	
}

