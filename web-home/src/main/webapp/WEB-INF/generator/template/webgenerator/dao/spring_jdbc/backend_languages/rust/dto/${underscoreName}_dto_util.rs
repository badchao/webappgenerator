
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
		let mut result = ${className}Dto::default();
		
		<@generateAllColumnsCopy/>
		return result;
    }
	
	
	pub fn to_list_dto(input: &Vec<${className}>) -> Vec<${className}Dto> {
		let mut result = Vec::new();
		for item in input {
			result.push(Self::to_dto(item));
		}
		return result;
	}

	pub fn from_list_dto(input: &Vec<${className}Dto>) -> Vec<${className}> {
		let mut result = Vec::new();
		for item in input {
			result.push(Self::from_dto(item));
		}
		return result;
	}	
}