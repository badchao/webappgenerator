<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

	${className} new${className}() {
		return ${className}DataFactory.new${className}();
	}
}
