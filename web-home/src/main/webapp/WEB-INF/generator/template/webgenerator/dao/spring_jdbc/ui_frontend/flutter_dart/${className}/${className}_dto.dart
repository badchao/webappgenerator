<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 

class ${className} {

<#list table.columns as column>
	//${column.columnAlias!}       db_column: ${column.sqlName}
	final <@dartType column/><#if column.nullable>?</#if> ${column.columnNameLower};
</#list>

  ${className}({
<#list table.columns as column>	
    <#if !column.nullable>required</#if> this.${column.columnNameLower},
</#list>    
  });
  
  
  
  static OdsEnvCheck newMockData(int index)  {
	String num = index.toString();
	return ${className}(
		<#list table.columns as column>
			<#if column.isStringColumn>
			${column.columnNameLower}: '${column.columnNameLower}',
			<#elseif column.isDateTimeColumn>
			${column.columnNameLower}: DateTime.now(),
			<#else>
			${column.columnNameLower}: <@dartType column/>.parse(num),
			</#if>
	    </#list>
	  );
  }
  
}