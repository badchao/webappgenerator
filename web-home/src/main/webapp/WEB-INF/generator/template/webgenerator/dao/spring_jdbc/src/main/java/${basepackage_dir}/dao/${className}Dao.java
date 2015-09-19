<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

<#include "/java_imports.include">

import java.util.List;
import com.github.rapid.common.util.page.Page;

/**
 * tableName: ${table.sqlName}
 * [${table.tableAlias}] 的Dao操作
 * 
<#include "/java_description.include">
*/
public interface ${className}Dao {
	
	public void insert(${className} entity);
	
	public int update(${className} entity);

	public int deleteById(<@generateArguments table.pkColumns/>);
	
	public ${className} getById(<@generateArguments table.pkColumns/>);
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.primitiveJavaType} ${column.columnNameFirstLower});
	
	</#if>
	</#list>

	public Page<${className}> findPage(${className}Query query);	
	
}
