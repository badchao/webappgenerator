<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign pkJavaType = table.idColumn.javaType>   
<#assign pkJavaVarName = table.pkColumn.columnNameFirstLower>

package ${basepackage}.controller.${namespace};


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import static org.junit.jupiter.api.Assertions.*;

import ${basepackage}.entity.${namespace}.*;
import ${basepackage}.query.${namespace}.*;
import ${basepackage}.service.${namespace}.*;


import org.beetl.sql.core.engine.PageQuery;

import ${basepackage}.basetest.BaseControllerTestCase;


public class ${className}ControllerTest extends BaseControllerTestCase {

	${className}Controller controller = new ${className}Controller();
	
	private ${className}Service ${classNameLower}Service = mock(${className}Service.class);
	
	private ${className} id = new${className}();
	
	@BeforeEach
	public void before(TestInfo testInfo) throws Exception {
    	System.out.println("\n------------------ "+testInfo.getDisplayName()+" ----------------------\n");
		controller.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@Test
	public void create() {
		${className} obj = new${className}();
		controller.create(obj);
		
		verify(${classNameLower}Service).insert(obj); //验证执行了该语句
	}
	

	@Test
	public void update() {
		${className} obj = new${className}();
		controller.update(obj);
		
		verify(${classNameLower}Service).updateById(any()); //验证执行了该语句
	}
	
	@Test
	public void remove() {
		controller.remove(id);
		
		verify(${classNameLower}Service).deleteById(id.id()); //验证执行了该语句
	}
	
	@Test
	public void getone() {
		when(${classNameLower}Service.getById(id)).thenReturn(new${className}()); // mock方法调用
		
		controller.getone(id);
		
		verify(${classNameLower}Service).getById(id.id()); //验证执行了该语句
	}
	
	
	@Test
	public void query() {
		${className}Query query = new${className}Query();
		when(${classNameLower}Service.query(query)).thenReturn(new PageQuery<${className}>()); // mock方法调用
		
		controller.query(query);
		
		verify(${classNameLower}Service).query(query); //验证执行了该语句
	}

	@Test
	public void download() {
		${className}Query query = new${className}Query();
		when(${classNameLower}Service.query(query)).thenReturn(new PageQuery<${className}>()); // mock方法调用
		
		controller.download(query);
		
		verify(${classNameLower}Service).query(query); //验证执行了该语句
	}
	
	private ${className}Query new${className}Query() {
		return new ${className}Query();
	}
	
	private ${className} new${className}() {
		return new ${className}();
	}
	
}
