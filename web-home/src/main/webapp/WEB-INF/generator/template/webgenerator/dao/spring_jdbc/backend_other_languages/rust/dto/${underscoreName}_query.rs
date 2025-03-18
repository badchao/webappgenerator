<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   



use serde::{Deserialize, Serialize};


#[derive(Debug, Serialize, Deserialize)]
pub struct ${className}Query{
	<#list table.columns as column>
	pub ${column.underscoreName} : Option<${column.javaType}>,
	</#list>
}

