
<#include "/java_copyright.include">
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   

use chrono::NaiveDateTime;
use serde::{Deserialize, Serialize};
use crate::generated::protobuf::generated_protobuf::${ClassName}Dto;
use crate::model::${underscoreName}::${ClassName};

struct ${ClassName}DtoUtil;

impl ${ClassName}DtoUtil{
    pub fn from_dto(input: &${ClassName}Dto) -> ${ClassName} {
        let result = ${ClassName} {
			<#list table.columns as column>
            ${column.underscoreName}: input.${column.underscoreName},
            </#list>
        };
        return result;
    }

	pub fn to_dto(input: &${ClassName}) -> ${ClassName}Dto {
        let mut result = ${ClassName}Dto{
			<#list table.columns as column>
            ${column.underscoreName}: input.${column.underscoreName},
            </#list>
		};
		return result;
    }
}