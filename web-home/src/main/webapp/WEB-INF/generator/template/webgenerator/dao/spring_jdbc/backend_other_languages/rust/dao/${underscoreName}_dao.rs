
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   

use axum::extract::RawPathParams;
use diesel::{prelude::*, pg::PgConnection, query_dsl::methods::BoxedDsl};
use crate::model::${underscoreName}::*;
use crate::dto::${underscoreName}_query::${className}Query;

use diesel::pg::Pg;


pub struct ${className}Dao;

impl ${className}Dao {


    pub fn create(conn: &mut PgConnection, entity: &${className}) -> QueryResult<${className}> {
        diesel::insert_into(${underscoreName}::table)
            .values(entity)
            .get_result(conn)
    }

    pub fn update_by_id(
        conn: &mut PgConnection,
        entity: &${className}
    ) -> QueryResult<${className}> {
        let id = ${className}Id {
			<#list table.pkColumns as column>
		    ${column.underscoreName}: entity.${column.underscoreName}.clone(),
			</#list>
        };

        let statement = ${underscoreName}::table
		<#list table.pkColumns as column>
			.filter(${underscoreName}::${column.underscoreName}.eq(id.${column.underscoreName}))
		</#list>;
        
        diesel::update(statement)
            .set(entity)
            .get_result(conn)
    }

    pub fn delete_by_id(
        conn: &mut PgConnection,
        id : &${className}Id
    ) -> QueryResult<usize> {
        let statement = ${underscoreName}::table
		<#list table.pkColumns as column>
			.filter(${underscoreName}::${column.underscoreName}.eq(id.${column.underscoreName}))
		</#list>;
        
        diesel::delete(statement)
            .execute(conn)
    }

    pub fn find_by_id(
        conn: &mut PgConnection, 
        id : &${className}Id
    ) -> QueryResult<${className}> {
        let statement = ${underscoreName}::table
		<#list table.pkColumns as column>
			.filter(${underscoreName}::${column.underscoreName}.eq(id.${column.underscoreName}))
		</#list>;

        statement.first(conn)
    }

    pub fn query(
        conn: &mut PgConnection, 
        params: &${className}Query
    ) -> QueryResult<Vec<${className}>> {
        let mut query = ${underscoreName}::table.into_boxed();
		<#list table.columns as column>
        if let Some(${column.underscoreName}) = &params.${column.underscoreName} {
            query = query.filter(${underscoreName}::${column.underscoreName}.eq(${column.underscoreName}));
        }
		</#list>

        query.load::<${className}>(conn)
    }


}