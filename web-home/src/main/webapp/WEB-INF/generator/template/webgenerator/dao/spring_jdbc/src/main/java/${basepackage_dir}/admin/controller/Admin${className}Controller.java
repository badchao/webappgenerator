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
 * [${table.tableAlias}] 的Admin Controller
 * 
<#include "/java_description.include">
 */
@Controller
@RequestMapping("/admin/${classNameLower}")
public class Admin${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${classNameLower}Service;

	@PostMapping
	public void create(@RequestBody ${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"w");
		
		${classNameLower}Service.create(${classNameLower});
	}
	
	@PostMapping
	public void update(@RequestBody ${className} ${classNameLower},HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"w");
		
		Integer id = ${classNameLower}.getId();

		//不可以让客户端可以更新所有属性
		${className} fromDb = ${classNameLower}Service.getById(id);
		BeanUtils.copyProperties(${classNameLower}, fromDb,"createTime"); //ignore some copy property
		
		${classNameLower}Service.update(fromDb);
	}
	
	@PostMapping
	public void removeById(int id,HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"w");
		
		${classNameLower}Service.removeById(id);
	}

	@GetMapping
	public ResponseEntity<?> getById(boolean join,int id,HttpServletRequest request) {
		checkActionPermission(request,${className}.class,"r");
		
		${className} result = ${classNameLower}Service.getById(id);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping
	public ResponseEntity<?> findPage(boolean join,${className}Query query,HttpServletRequest request){
		checkActionPermission(request,${className}.class,"r");
		
		Page<${className}> page = ${classNameLower}Service.findPage(query);
		return ResponseEntity.ok(page);
	}
	
}

