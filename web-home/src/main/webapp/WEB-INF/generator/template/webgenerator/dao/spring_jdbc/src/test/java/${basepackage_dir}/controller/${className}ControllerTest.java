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
	public void remove() {
		controller.remove(id);
		
		verify(${classNameLower}Service).remove(id); //验证执行了该语句
	}
	
	@Test
	public void getone() {
		when(${classNameLower}Service.getone(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} ${classNameLower} = controller.getone(id);
		
		verify(${classNameLower}Service).getone(id); //验证执行了该语句
		assertNotNull(${classNameLower});
	}
	
	
	@Test
	public void query() {
		${className}Query query = new${className}Query();
		when(${classNameLower}Service.query(query)).thenReturn(new Page()); // mock方法调用
		
		Page page = controller.query(query);
		
		verify(${classNameLower}Service).query(query); //验证执行了该语句
		assertNotNull(page);
	}

	@Test
	public void download() {
		${className}Query query = new${className}Query();
		when(${classNameLower}Service.query(query)).thenReturn(new Page()); // mock方法调用
		
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
