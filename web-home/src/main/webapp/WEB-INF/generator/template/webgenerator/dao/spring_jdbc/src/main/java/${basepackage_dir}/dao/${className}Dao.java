<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import ${basepackage}.model.*;
import ${basepackage}.query.*;

import java.util.*;
import com.github.rapid.common.util.page.Page;

/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作
 * 
<#include "/java_description.include">
*/
public interface ${className}Dao extends Dao {
	
	public void insert(${className} entity);
	
	public int update(${className} entity);

	public int deleteById(${className} entity);
	
	public ${className} getById(${className} entity);
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower});
	
	</#if>
	</#list>

	public Page<${className}> query(${className}Query query);	
	
	public List<${className}> findList(${className}Query query);
	
}
