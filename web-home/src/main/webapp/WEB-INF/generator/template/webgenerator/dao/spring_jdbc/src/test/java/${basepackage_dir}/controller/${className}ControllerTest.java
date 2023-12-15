<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign pkJavaType = table.idColumn.javaType>   
<#assign pkJavaVarName = table.pkColumn.columnNameFirstLower>

package ${basepackage}.controller;


import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import ${basepackage}.model.*;
import ${basepackage}.query.*;
import ${basepackage}.service.*;

import com.github.rapid.common.util.page.Page;

public class ${className}ControllerTest extends BaseControllerTestCase {

	${className}Controller controller = new ${className}Controller();
	
	private ${className}Service ${classNameLower}Service = mock(${className}Service.class);
	
	@Rule public TestName testName = new TestName();
	
	private ${className} id = new${className}();
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
		controller.${classNameLower}Service = ${classNameLower}Service;
	}
	
	@Test
	public void create() {
		${className} obj = new${className}();
		controller.create(obj);
		
		verify(${classNameLower}Service).create(obj); //验证执行了该语句
	}
	

	@Test
	public void update() {
		${className} obj = new${className}();
		controller.update(obj);
		
		verify(${classNameLower}Service).updateByManual(any()); //验证执行了该语句
	}
	
	@Test
	public void removeById() {
		controller.removeById(id);
		
		verify(${classNameLower}Service).removeById(id); //验证执行了该语句
	}
	
	@Test
	public void getById() {
		when(${classNameLower}Service.getById(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} ${classNameLower} = controller.getById(id);
		
		verify(${classNameLower}Service).getById(id); //验证执行了该语句
		assertNotNull(${classNameLower});
	}
	
	
	@Test
	public void findPage() {
		${className}Query query = new ${className}Query();
		when(${classNameLower}Service.findPage(query)).thenReturn(new Page()); // mock方法调用
		
		Page page = controller.findPage(query);
		
		verify(${classNameLower}Service).findPage(query); //验证执行了该语句
		assertNotNull(page);
	}
	
	private ${className} new${className}() {
		return new ${className}();
	}
	
}
