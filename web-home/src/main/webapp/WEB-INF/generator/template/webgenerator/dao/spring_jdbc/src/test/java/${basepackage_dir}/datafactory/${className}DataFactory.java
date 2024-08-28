<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.datafactory;

import java.util.*;

import ${basepackage}.model.*;
import ${basepackage}.query.*;

/**
 * 生成${className}相关数据对象的默认值
 * 用于测试数据生成
 * 
<#include "/java_description.include">
 * 
 */
public class ${className}DataFactory {
	
	public static ${className}Query new${className}Query() {
		${className}Query obj = new ${className}Query();
		obj.setPage(1);
		obj.setPageSize(10);
		obj.setKeyword("1");
		
		<#list table.columns as column>
	  		<#if column.isDateTimeColumn && !column.contains("begin,start,end")>
	  	obj.set${column.columnName}Begin(new ${column.javaType}(System.currentTimeMillis()));
	  	obj.set${column.columnName}End(new ${column.javaType}(System.currentTimeMillis()));
  			<#elseif column.isStringColumn>
  		obj.set${column.columnName}("${column.columnNameLower}");
  			<#elseif column.isNumberColumn>
		obj.set${column.columnName}(new ${column.javaType}("1"));	  	
			<#else>
	  		obj.set${column.columnName}(new ${column.javaType}("1"));
			</#if>
		</#list>
		return obj;
	}
	
	public static ${className} new${className}() {
		${className} obj = new ${className}();
		
		<#list table.columns as column>
  			<#if column.isDateTimeColumn>
	  	obj.set${column.columnName}(new ${column.javaType}(System.currentTimeMillis()));
	  		<#elseif column.isStringColumn>
		obj.set${column.columnName}("${column.columnNameLower}");
  		<#elseif column.isNumberColumn>
	  	obj.set${column.columnName}(new ${column.javaType}("1"));	  	
  			<#else>
	  	obj.set${column.columnName}(new ${column.javaType}("1"));
  			</#if>
		</#list>
		return obj;
	}
}