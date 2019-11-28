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

import ${basepackage}.${className}DataFactory;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

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
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
		service.set${className}Dao(${classNameLower}Dao);
	}
	
	@Test
	public void test_create() {
		${className} obj = ${className}DataFactory.new${className}();
		service.create(obj);
		
		verify(${classNameLower}Dao).insert(obj); //验证执行了该语句
	}
	
	@Test
	public void test_update() {
		when(${classNameLower}Dao.getById(<@generateArgumentsWithRandomValue table.pkColumns/>)).thenReturn(${className}DataFactory.new${className}()); // mock方法调用
		
		${className} obj = ${className}DataFactory.new${className}();
		service.update(obj);
		
		verify(${classNameLower}Dao).update(obj); //验证执行了该语句
	}
	
	@Test
	public void test_removeById() {
		service.removeById(<@generateArgumentsWithRandomValue table.pkColumns/>);
		
		verify(${classNameLower}Dao).deleteById(<@generateArgumentsWithRandomValue table.pkColumns/>); //验证执行了该语句
	}
	
	@Test
	public void test_getById() {
		when(${classNameLower}Dao.getById(<@generateArgumentsWithRandomValue table.pkColumns/>)).thenReturn(${className}DataFactory.new${className}()); // mock方法调用
		
		${className} ${classNameLower} = service.getById(<@generateArgumentsWithRandomValue table.pkColumns/>);
		
		verify(${classNameLower}Dao).getById(<@generateArgumentsWithRandomValue table.pkColumns/>); //验证执行了该语句
		assertNotNull(${classNameLower});
	}
	
	
}

