<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${basepackage}.datafactory.${className}DataFactory;
import ${basepackage}.query.${className}Query;

import ${basepackage}.datafactory.${className}DataFactory;
import ${basepackage}.mapper.${className}Mapper;
import ${basepackage}.model.${className};

public class ${className}MapperTest extends BaseDaoTestCase {
	
	@Autowired
    private ${className}Mapper ${classNameLower}Mapper;

	@Test
	public void selectById() {
		${classNameLower}Mapper.selectBy<@mybatisJavaIdMethod/>(new${className}());
	}
	
	@Test
	public void deleteById() {
		${classNameLower}Mapper.deleteBy<@mybatisJavaIdMethod/>(new${className}());
	}
	
	@Test
	public void updateById() {
		${classNameLower}Mapper.updateBy<@mybatisJavaIdMethod/>(new${className}());
	}

	@Test
	public void insert() {
		${classNameLower}Mapper.insert(new${className}());
	}

	@Test
	public void queryPage() {
		Page page = new Page(1,100);
		${className}Query query = ${className}DataFactory.new${className}Query();
		
		IPage result = ${classNameLower}Mapper.queryPage(page, query);
		
		assertNotNull(result);
		//assertEquals(result.getTotal(),0);
	}
	
	${className} new${className}() {
		return ${className}DataFactory.new${className}();
	}
}
