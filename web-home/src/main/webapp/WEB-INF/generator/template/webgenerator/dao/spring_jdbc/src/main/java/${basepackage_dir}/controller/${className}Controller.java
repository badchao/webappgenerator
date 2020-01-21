<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.controller;

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
 * [${table.tableAlias}] 用户前台  Controller
 * 
<#include "/java_description.include">
 */

@Controller
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${classNameLower}Service;

	public void set${className}Service(${className}Service ${classNameLower}Service) {
		this.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@PostMapping
	public ResponseEntity<?> create(${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},"w");
		
		${classNameLower}Service.create(${classNameLower});
		return ResponseEntity.ok("success");
	}
	
	@PostMapping
	public ResponseEntity<?> update(${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},"w");
		
		${classNameLower}Service.updateByManual(${classNameLower});
		return ResponseEntity.ok("success");
	}
	
	@PostMapping
	public ResponseEntity<?> removeById(${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},"w");
		
		${classNameLower}Service.removeById(${classNameLower});
		return ResponseEntity.ok("success");
	}

	@GetMapping
	public ResponseEntity<?> getById(boolean join,${className} ${classNameLower},HttpServletRequest request) {
		checkEntityPermission(request,${classNameLower},"r");
		
		${className} result = ${classNameLower}Service.getById(${classNameLower});
		if(join) ${classNameLower}Service.join(result);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping
	public ResponseEntity<?> findPage(boolean join,${className}Query query,${className} ${classNameLower},HttpServletRequest request){
		checkEntityPermission(request,${classNameLower},"r");
		
		Page<${className}> result = ${classNameLower}Service.findPage(query);
		if(join) result.forEach(${classNameLower}Service::join);
		
		return ResponseEntity.ok(result);
	}
	
}

