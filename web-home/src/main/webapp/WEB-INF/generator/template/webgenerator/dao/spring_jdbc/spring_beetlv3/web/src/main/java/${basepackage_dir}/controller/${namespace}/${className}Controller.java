<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

package ${basepackage}.controller.${namespace};


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


import ${basepackage}.entity.${namespace}.${className};
import ${basepackage}.query.${namespace}.${className}Query;
import ${basepackage}.service.${namespace}.${className}Service;

import java.util.Map;
import java.util.HashMap;

import com.modo.app.core.service.util.AppContextUtil;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.page.PageResult; // beetl v3.0

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.modo.app.core.service.util.AppContextUtil;
import com.modo.cloud.common.result.ResultBean;

import ${basepackage}.base.BaseController;

/**
 * [${table.tableAlias}] Controller
 * 
<#include "/java_description.include">
 */

@RestController
@RequestMapping("/${classNameLower}")
@Tag(name = "${className}-${table.tableAlias}")
public class ${className}Controller  extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	
    @Autowired
    public ${className}Service ${classNameLower}Service;

	
	@Operation(summary="元数据查询,得到所有相关枚举等元数据,返回所有搜索条件")
	@GetMapping("meta")
	public ResultBean<Map<String,Object>> meta() {
		String tenantId = AppContextUtil.getTenantId();
		
		// Map<EnumClassName,Map<EnumName,EnumDesc>>
		Map<String,Object> result = new HashMap<String,Object>();
		return ResultBean.success(result);
	}
	
	@Operation(summary="创建")
	@PostMapping("create")
	public ResultBean<${className}> create(@RequestBody ${className} ${classNameLower}) {
        String userId = AppContextUtil.getUserId();
        String shopId = AppContextUtil.getShopId();
        String tenantId = AppContextUtil.getTenantId();
        
        ${classNameLower}.setCreateUserId(userId);
		${classNameLower}Service.insert(${classNameLower});
		
		return ResultBean.success(${classNameLower});
	}
	
	@Operation(summary="修改")
	@PostMapping("update")
	public ResultBean<${className}> update(@RequestBody ${className} ${classNameLower}) {
        String userId = AppContextUtil.getUserId();
        String shopId = AppContextUtil.getShopId();
        String tenantId = AppContextUtil.getTenantId();
        
        ${classNameLower}.setUpdateUserId(userId);
		${classNameLower}Service.updateById(${classNameLower});
		
		return ResultBean.success(${classNameLower});
	}
	
	@Operation(summary="根据ID删除")
	@PostMapping("remove")
	public ResultBean<Boolean> remove(@RequestBody ${className} ${classNameLower}) {
        String userId = AppContextUtil.getUserId();
        String shopId = AppContextUtil.getShopId();
        String tenantId = AppContextUtil.getTenantId();
        
        ${table.pkColumn.javaType} id = ${classNameLower}.id();
		${classNameLower}Service.deleteById(id);
		
		return ResultBean.success(true);
	}

	@Operation(summary="根据ID查找")
	@PostMapping("getone")
	public ResultBean<${className}> getone(@RequestBody ${className} ${classNameLower}) {
        String userId = AppContextUtil.getUserId();
        String shopId = AppContextUtil.getShopId();
        String tenantId = AppContextUtil.getTenantId();
        
        ${table.pkColumn.javaType} id = ${classNameLower}.id();
		${className} result = ${classNameLower}Service.getById(id);
		${classNameLower}Service.join(result);
		
		return ResultBean.success(result);
	}
	
	@Operation(summary="分页查询")
	@PostMapping("query")
	public ResultBean<PageResult<${className}>> query(@RequestBody ${className}Query query){
		PageResult<${className}> page = query0(query);
		return ResultBean.success(page);
	}
	
	PageResult<${className}> query0(@RequestBody ${className}Query query){
        String userId = AppContextUtil.getUserId();
        String shopId = AppContextUtil.getShopId();
        String tenantId = AppContextUtil.getTenantId();
        
        query.setTenantId(tenantId);
        
		PageResult<${className}> page = ${classNameLower}Service.query(query);
		return page;
	}
	
	@Operation(summary="导出下载")
	@PostMapping("download")
	public void download(@RequestBody ${className}Query query)  {
        query.setPageSize(1000);
        
        PageResult<${className}> page = query0(query);
//		writeExcel2Response(getResponse(),page.getList(),${className}.class);
	}
	
}

