<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   



use serde::{Deserialize, Serialize};


#[derive(Debug,Clone, Serialize, Deserialize)]
pub struct ${className}Query{
	<#list table.columns as column>
	pub ${column.underscoreName} : Option<<@rustType column/>>,
	</#list>
}

