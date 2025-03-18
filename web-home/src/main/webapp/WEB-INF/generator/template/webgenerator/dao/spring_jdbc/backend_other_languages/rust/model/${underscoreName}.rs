<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   





use serde::{Deserialize, Serialize};
use diesel::{Queryable, Insertable, AsChangeset};
use diesel::sql_types::{BigInt, Text, Numeric};
use chrono::NaiveDateTime;

<#if 1 < table.pkCount>
#[derive(Debug,Queryable, Serialize, Deserialize)]
pub struct ${className}Id {
	<#list table.pkColumns as column>
    pub ${column.underscoreName} : <@rustType column/>,
	</#list>
}
</#if>

#[derive(Debug, Insertable,Queryable, Serialize, Deserialize, AsChangeset)]
#[diesel(table_name = ${underscoreName})]
pub struct ${className} {
	<#list table.columns as column>
	/**
	 * ${column.columnAlias!}       db_column: ${column.sqlName} 
	 */
	<#if column.pk>
	</#if>
	pub ${column.underscoreName} : <@rustTypeWithOption column/>,

	</#list>
    
}

diesel::table! {
    ${underscoreName} (<#list table.pkColumns as column>${column.underscoreName}<#if column_has_next>,</#if></#list>) {
	<#list table.columns as column>
		<#if column.pk>
		#[diesel(primary_key)]
	    </#if>
		${column.underscoreName} -> <@rustDieselTableTypeWithOption column/>,
		
	</#list>
    }
}

