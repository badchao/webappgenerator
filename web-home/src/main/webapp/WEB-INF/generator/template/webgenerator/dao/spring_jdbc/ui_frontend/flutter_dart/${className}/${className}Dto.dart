<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 
<#assign classNameDtoClass = className+"Dto"> 


import 'common_import.dart';


class ${classNameDtoClass} {

<#list table.columns as column>
	//${column.columnAlias!}       db_column: ${column.sqlName}
	<@dartType column/><#if column.nullable>?</#if> ${column.columnNameLower};
</#list>

  ${classNameDtoClass}({
<#list table.columns as column>	
    <#if !column.nullable>required</#if> this.${column.columnNameLower},
</#list>    
  });
  
  
  
  static ${classNameDtoClass} newMockData(int index)  {
	String num = index.toString();
	return ${classNameDtoClass}(
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

class ${className}PageRequest {
<#list table.columns as column>
	<@dartType column/>? ${column.columnNameLower};
</#list>	
}

// add other compute fields
extension ${classNameDtoClass}Extension on ${classNameDtoClass} {
	
}


	