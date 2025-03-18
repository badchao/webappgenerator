
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

    pub fn create(conn: &mut PgConnection, ${underscoreName}: ${className}) -> Result<${className}> {
        return ${className}Dao::create(conn, &${underscoreName})
            .context("create error");
    }

    pub fn update_by_id(
        conn: &mut PgConnection,
        entity: ${className}
    ) -> Result<${className}> {
        return ${className}Dao::update_by_id(conn, &updated)
            .context("update error");
    }

    pub fn delete_by_id(
        conn: &mut PgConnection,
        id : ${className}Id
    ) -> Result<usize> {
        return ${className}Dao::delete_by_id(conn, &id)
            .context("delete error");
    }


    pub fn find_by_id(conn: &mut PgConnection, id: ${className}Id) -> Result<${className}> {
        return ${className}Dao::find_by_id(conn, &id)
            .context(format!("query error,entity: {id:?}"));
    }

    pub fn query(conn: &mut PgConnection, params: &${className}Query) -> Result<Vec<${className}>> {
        return ${className}Dao::query(conn, params)
            .context(format!("query error,entity: {params:?}"));
    }

}