<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.datafactory;

import java.util.*;

import ${basepackage}.model.*;
import ${basepackage}.query.*;

/**
 * 用于生成${className}相关数据对象的默认值
 * 
<#include "/java_description.include">
 * 
 */
public class ${className}DataFactory {
	
	public static ${className}Query new${className}Query() {
		${className}Query query = new ${className}Query();
		query.setPage(1);
		query.setPageSize(10);
		query.setKeyword("1");
		
		<#list table.columns as column>
	  		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
		query.set${column.columnName}Begin(new ${column.javaType}(System.currentTimeMillis()));
		query.set${column.columnName}End(new ${column.javaType}(System.currentTimeMillis()));
			<#else>
	  	query.set${column.columnName}(new ${column.simpleJavaType}("1"));
			</#if>
		</#list>
		return query;
	}
	
	public static ${className} new${className}() {
		${className} obj = new ${className}();
		
		<#list table.columns as column>
  			<#if column.isDateTimeColumn>
	  	obj.set${column.columnName}(new ${column.javaType}(System.currentTimeMillis()));
  			<#else>
	  	obj.set${column.columnName}(new ${column.javaType}("1"));
  			</#if>
		</#list>
		return obj;
	}
}