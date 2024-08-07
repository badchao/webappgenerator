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

import com.github.rapid.common.util.page.Page;

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
	public void removeById(@RequestBody ${className} ${classNameLower}) {
		checkEntityPermission(getRequest(),${classNameLower},DELETE);
		
		${classNameLower}Service.removeById(${classNameLower});
	}

	@ApiOperation(value="根据ID查找")
	@GetMapping
	public ${className} getById(${className} ${classNameLower}) {
		checkEntityPermission(getRequest(),${classNameLower},READ);
		
		${className} result = ${classNameLower}Service.getById(${classNameLower});
		${classNameLower}Service.join(result);
		
		return result;
	}
	
	@ApiOperation(value="分页查询")
	@GetMapping
	public Page<${className}> findPage(${className}Query query){
		checkEntityPermission(getRequest(),new ${className}(),READ);
		
		Page<${className}> result = ${classNameLower}Service.findPage(query);
		result.forEach(${classNameLower}Service::join);
		
		return result;
	}
	
	@ApiOperation(value="导出下载")
	@GetMapping
	public void download(${className}Query query) throws java.io.IOException {
		Page<${className}> result = findPage(query);
		
		Class headClazz = ${className}.class;
		
		String fileName = "download_" + headClazz.getSimpleName() + ".xlsx";
		HttpServletResponse response = getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        EasyExcel.write(response.getOutputStream(), headClazz)
//        .excelType(ExcelTypeEnum.CSV)
//        .inMemory(true)
        .sheet("sheet1")
        .doWrite(result.getItemList());
	}
}

