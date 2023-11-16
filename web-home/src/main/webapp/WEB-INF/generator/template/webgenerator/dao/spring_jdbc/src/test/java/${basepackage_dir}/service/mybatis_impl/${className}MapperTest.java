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
public class ${className}MapperTest extends BaseDaoTestCase {
	
	@Autowired
    private ${className}Mapper ${classNameLower}Mapper;

	@Test
	public void selectById() {
		${classNameLower}Mapper.selectById(new${className}());
	}
	
	@Test
	public void deleteById() {
		${classNameLower}Mapper.deleteById(new${className}());
	}
	
	@Test
	public void updateById() {
		${classNameLower}Mapper.updateById(new${className}());
	}

	@Test
	public void insert() {
		${classNameLower}Mapper.insert(new${className}());
	}

	${className} new${className}() {
		return ${className}DataFactory.new${className}();
	}
}
