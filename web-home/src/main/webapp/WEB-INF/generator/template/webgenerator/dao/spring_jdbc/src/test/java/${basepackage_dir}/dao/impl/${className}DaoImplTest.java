<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.impl;

import ${basepackage}.datafactory.${className}DataFactory;

import com.github.rapid.common.util.page.Page;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;



import static org.junit.Assert.*;
import ${basepackage}.model.*;
import ${basepackage}.query.*;
import ${basepackage}.dao.*;


/**
<#include "/java_description.include">
 */
public class ${className}DaoImplTest extends BaseDaoTestCase {
	
	@Rule public TestName testName = new TestName();
	
	private ${className}Dao dao;
	
	private ${className} id = new ${className}(<@generateArgumentsWithRandomValue table.pkColumns/>);
	
	@Before
	public void before() {
		System.out.println("\n------------------ "+testName.getMethodName()+" ----------------------\n");
	}
	
	@Autowired
	public void set${className}Dao(${className}Dao dao) {
		this.dao = dao;
	}

	//数据库单元测试前会开始事务，结束时会回滚事务，所以测试方法可以不用关心测试数据的删除
	@Test
	public void findPage() {

		${className}Query query = ${className}DataFactory.new${className}Query();
		Page page = dao.findPage(query);
		
		assertEquals(1,page.getPaginator().getPage());
		assertEquals(10,page.getPaginator().getPageSize());
		List resultList = (List)page.getItemList();
		assertNotNull(resultList);
		
	}
	
	@Test
	public void insert() {
		dao.insert(${className}DataFactory.new${className}());
	}
	
	@Test
	public void update() {
		dao.update(${className}DataFactory.new${className}());
	}
	
	@Test
	public void delete() {
		dao.deleteById(id);
	}
	
	@Test
	public void getById() {
		dao.getById(id);
	}
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Test
	public void getBy${column.columnName}() {
		dao.getBy${column.columnName}(new ${column.javaType}("1"));
	}
	
	</#if>
	</#list>
	
}

