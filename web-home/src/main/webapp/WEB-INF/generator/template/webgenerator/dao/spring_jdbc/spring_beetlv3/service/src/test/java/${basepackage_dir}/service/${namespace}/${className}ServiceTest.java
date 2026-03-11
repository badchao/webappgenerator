<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign pkJavaType = table.idColumn.javaType>   
<#assign pkJavaVarName = table.pkColumn.columnNameFirstLower>

package ${basepackage}.service.${namespace};

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



import ${basepackage}.datafactory.${namespace}.${className}DataFactory;
import ${basepackage}.entity.${namespace}.*;
import ${basepackage}.query.${namespace}.*;
import ${basepackage}.beetldao.${namespace}.*;

/**
<#include "/java_description.include">
 */
public class ${className}ServiceTest {

	//mock框架使用Mockito 具体使用请查看: http://code.google.com/p/mockito/wiki/MockitoVSEasyMock
	
	private ${className}Service service = new ${className}Service();
	private ${className}Dao ${classNameLower}Dao = mock(${className}Dao.class);
	
	@Rule public TestName testName = new TestName();
	
	private ${className} id = new${className}();
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
		service.${classNameLower}Dao = ${classNameLower}Dao;
		service.setDao(${classNameLower}Dao);
	}
	
	@Test
	public void insert() {
		${className} obj = new${className}();
		service.insert(obj);
		
		verify(${classNameLower}Dao).insert(obj); //验证执行了该语句
	}
	
	@Test
	public void updateById() {
		when(${classNameLower}Dao.single(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} obj = new${className}();
		service.updateById(obj);
		
		verify(${classNameLower}Dao).updateById(obj); //验证执行了该语句
	}
	
	@Test
	public void deleteById() {
		when(${classNameLower}Dao.single(id)).thenReturn(new${className}()); // mock方法调用
		
		service.deleteById(id);
		
		verify(${classNameLower}Dao).deleteById(id); //验证执行了该语句
	}
	
	@Test
	public void single() {
		when(${classNameLower}Dao.single<@mybatisJavaIdMethod/>(id)).thenReturn(new${className}()); // mock方法调用
		
		${className} ${classNameLower} = service.single(id);
		
		verify(${classNameLower}Dao).single(id); //验证执行了该语句
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

