
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   

use chrono::NaiveDateTime;
use serde::{Deserialize, Serialize};
use crate::generated::protobuf::generated_protobuf::${className}Dto;
use crate::model::${underscoreName}::${className};

struct ${className}DtoUtil;

<#macro generateAllColumnsCopy>
	<#list table.columns as column>
		result.${column.underscoreName} = input.${column.underscoreName};
	</#list>
</#macro>

impl ${className}DtoUtil{
	
    pub fn from_dto(input: &${className}Dto) -> ${className} {
		let input = input.clone();
		let mut result = ${className}::default();
		
		<@generateAllColumnsCopy/>
        return result;
    }

	pub fn to_dto(input: &${className}) -> ${className}Dto {
		let input = input.clone();
		let mut result = OdsEnvCheckDto::default();
		
		<@generateAllColumnsCopy/>
		return result;
    }
	
}