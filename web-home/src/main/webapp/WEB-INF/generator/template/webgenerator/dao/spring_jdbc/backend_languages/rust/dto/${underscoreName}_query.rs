<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   



use serde::{Deserialize, Serialize};
use chrono::NaiveDateTime;
use chrono::NaiveDate;
use diesel::sql_types::*;

#[derive(Debug,Clone, Serialize, Deserialize)]
pub struct ${className}Query{
	<#list table.columns as column>
	pub ${column.underscoreName} : Option<<@rustType column/>>,
	</#list>
}

