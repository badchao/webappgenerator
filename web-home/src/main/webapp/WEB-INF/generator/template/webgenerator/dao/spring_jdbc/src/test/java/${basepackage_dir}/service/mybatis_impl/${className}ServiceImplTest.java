<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign pkJavaType = table.idColumn.javaType>   
<#assign pkJavaVarName = table.pkColumn.columnNameFirstLower>

package ${basepackage}.service.mybatis_impl;

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
import ${basepackage}.mapper.*;

/**
<#include "/java_description.include">
 */
public class ${className}ServiceImplTest {

	//mock框架使用Mockito 具体使用请查看: http://code.google.com/p/mockito/wiki/MockitoVSEasyMock
	
	private ${className}ServiceImpl service = new ${className}ServiceImpl();
	private ${className}Mapper ${classNameLower}Mapper = mock(${className}Mapper.class);
	
	@Rule public TestName testName = new TestName();
	
	private ${className} id = new${className}();
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
		service.set${className}Mapper(${classNameLower}Mapper);
	}
	
	@Test
	public void create() {
		${className} obj = new${className}();
		service.create(obj);
		
		verify(${classNameLower}Mapper).insert(obj); //验证执行了该语句
	}
	
	@Test
	public void update() {
		when(${classNameLower}Mapper.selectBy<@mybatisJavaIdMethod/>(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} obj = new${className}();
		service.update(obj);
		
		verify(${classNameLower}Mapper).updateBy<@mybatisJavaIdMethod/>(obj); //验证执行了该语句
	}
	
	@Test
	public void remove() {
		service.remove(id);
		
		verify(${classNameLower}Mapper).deleteBy<@mybatisJavaIdMethod/>(id); //验证执行了该语句
	}
	
	@Test
	public void getone() {
		when(${classNameLower}Mapper.selectBy<@mybatisJavaIdMethod/>(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} ${classNameLower} = service.getone(id);
		
		verify(${classNameLower}Mapper).selectBy<@mybatisJavaIdMethod/>(id); //验证执行了该语句
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

