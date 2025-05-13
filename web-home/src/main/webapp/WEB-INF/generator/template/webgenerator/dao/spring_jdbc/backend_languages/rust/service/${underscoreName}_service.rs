
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   

use diesel::result::Error as DieselError;
use diesel::{prelude::*, pg::PgConnection};
use std::error::Error;
use anyhow::{Context, Result};

use crate::dao::${underscoreName}_dao::${className}Dao;
use crate::dto::${underscoreName}_query::${className}Query;
use crate::model::${underscoreName}::{${className}, ${className}Id};



pub struct ${className}Service;

impl ${className}Service {
	
	pub fn check(${underscoreName}: &${className}) -> Result<${className}> {
		Ok(${underscoreName}.clone())
	}
	
    pub fn create(conn: &mut PgConnection, ${underscoreName}: ${className}) -> Result<${className}> {
        Self::check(&${underscoreName});
        let result = ${className}Dao::insert(conn, &${underscoreName})
            .context("create error");
        return result;
    }

    pub fn update_by_id(conn: &mut PgConnection,${underscoreName}: ${className}) -> Result<${className}> {
    	Self::check(&${underscoreName});
        let result = ${className}Dao::update_by_id(conn, &${underscoreName})
            .context("update error");
		return result;
    }

    pub fn delete_by_id(conn: &mut PgConnection,id : ${className}Id) -> Result<usize> {
        let result = ${className}Dao::delete_by_id(conn, &id)
            .context("delete error");
		return result;
    }


    pub fn get_by_id(conn: &mut PgConnection, id: ${className}Id) -> Result<${className}> {
        return ${className}Dao::get_by_id(conn, &id)
            .context(format!("query error,entity: {id:?}"));
    }

    pub fn query(conn: &mut PgConnection, params: &${className}Query) -> Result<Vec<${className}>> {
        return ${className}Dao::query(conn, params)
            .context(format!("query error,entity: {params:?}"));
    }

}