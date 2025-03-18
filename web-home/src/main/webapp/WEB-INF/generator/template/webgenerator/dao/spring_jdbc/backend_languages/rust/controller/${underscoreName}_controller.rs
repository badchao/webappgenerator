
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign underscoreName = table.underscoreName>   



use actix_web::{web, App, HttpResponse, HttpServer};
use actix_web::{get, middleware, post, HttpRequest, Responder};
use actix_web::{Result};
use misc_rs::dto::api_response::ApiResponse;
use std::collections::HashMap;
use serde_json::Value;

use crate::service::${underscoreName}_service::${className}Service;
use crate::config::config::get_ds_connection;
use crate::dto::${underscoreName}_query::${className}Query;
use crate::model::${underscoreName}::{${className}, ${className}Id};

pub fn service_config_${underscoreName}(cfg: &mut web::ServiceConfig) {
    cfg.service(meta);
    cfg.service(update);
    cfg.service(create);
    cfg.service(delete);
    cfg.service(get_by_id);
}

#[get("/meta")]
async fn meta()  -> Result<impl Responder> {
    let mut result: HashMap<&str, Value> = HashMap::new();

    Ok(web::Json(ApiResponse::success(result)))
}


#[post("/update")]
async fn update(entity : web::Json<${className}>)  -> Result<impl Responder> {
    let result = ${className}Service::update_by_id(&mut get_ds_connection(), entity.into_inner());
    Ok(web::Json(ApiResponse::result(result)))
}


#[post("/create")]
async fn create(entity : web::Json<${className}>)  -> Result<impl Responder> {
    let result = ${className}Service::create(&mut get_ds_connection(), entity.into_inner());
    Ok(web::Json(ApiResponse::result(result)))
}


#[post("/remove")]
async fn remove(entity : web::Json<${className}Id>)  -> Result<impl Responder> {
    let result = ${className}Service::delete_by_id(&mut get_ds_connection(), entity.into_inner());
    Ok(web::Json(ApiResponse::result(result)))
}

#[post("/get_by_id")]
async fn get_by_id(entity : web::Json<${className}Id>)  -> Result<impl Responder> {
    let result = ${className}Service::get_by_id(&mut get_ds_connection(), entity.into_inner());
    Ok(web::Json(ApiResponse::result(result)))
}

#[post("/query")]
async fn query(entity : web::Json<${className}Query>)  -> impl Responder {
    let result = ${className}Service::query(&mut get_ds_connection(), &entity.into_inner());
    web::Json(ApiResponse::result(result))
}
