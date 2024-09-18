<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign pkJavaType = table.idColumn.javaType>   
<#assign pkJavaVarName = table.pkColumn.columnNameFirstLower>

package ${basepackage}.service.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import ${basepackage}.datafactory.${className}DataFactory;
import ${basepackage}.model.*;
import ${basepackage}.query.*;
import ${basepackage}.dao.*;

/**
<#include "/java_description.include">
 */
public class ${className}ServiceImplTest {

	//mock框架使用Mockito 具体使用请查看: http://code.google.com/p/mockito/wiki/MockitoVSEasyMock
	
	private ${className}ServiceImpl service = new ${className}ServiceImpl();
	private ${className}Dao ${classNameLower}Dao = mock(${className}Dao.class);
	
	@Rule public TestName testName = new TestName();
	
	private ${className} id = new${className}();
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
		service.set${className}Dao(${classNameLower}Dao);
	}
	
	@Test
	public void create() {
		${className} obj = new${className}();
		service.create(obj);
		
		verify(${classNameLower}Dao).insert(obj); //验证执行了该语句
	}
	
	@Test
	public void update() {
		when(${classNameLower}Dao.getById(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} obj = new${className}();
		service.update(obj);
		
		verify(${classNameLower}Dao).update(obj); //验证执行了该语句
	}
	
	@Test
	public void removeById() {
		service.removeById(id);
		
		verify(${classNameLower}Dao).deleteById(id); //验证执行了该语句
	}
	
	@Test
	public void getById() {
		when(${classNameLower}Dao.getById(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} ${classNameLower} = service.getById(id);
		
		verify(${classNameLower}Dao).getById(id); //验证执行了该语句
		assertNotNull(${classNameLower});
	}
	
	@Test
	public void check() {
		${className} obj = new${className}();
		service.check(obj);
	}
	
	public ${className} new${className}() {
		return ${className}DataFactory.new${className}();
	}
}

