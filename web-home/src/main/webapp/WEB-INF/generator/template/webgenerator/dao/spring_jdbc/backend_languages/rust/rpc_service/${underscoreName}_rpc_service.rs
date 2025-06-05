
use crate::dto::ods_env_check_query::OdsEnvCheckQuery;
/**
 *  OdsEnvCheck rotobuf rpc service
 * 
 */
use crate::generated::protobuf::generated_protobuf::ods_env_check_rpc_service_server::{
    OdsEnvCheckRpcService, OdsEnvCheckRpcServiceServer
};
use crate::generated::protobuf::generated_protobuf::{
    OdsEnvCheckDto
};
use crate::dto::ods_env_check_dto_util::OdsEnvCheckDtoUtil;
use crate::generated::protobuf::generated_protobuf::*;
use crate::generated::protobuf::generated_protobuf;
use crate::model::ods_env_check::OdsEnvCheckId;
use crate::service::ods_env_check_service::OdsEnvCheckService;
use tonic::{Request, Response, Status, transport::Server};
use crate::config::get_ds_connection;
use chrono::{NaiveDateTime, TimeZone};

pub struct OdsEnvCheckRpcServiceImpl {}

#[tonic::async_trait]
impl OdsEnvCheckRpcService for OdsEnvCheckRpcServiceImpl {
    
    async fn meta(
        &self,
        request: tonic::Request<EmptyRequest>,
    ) -> std::result::Result<tonic::Response<OdsEnvCheckMetaResponse>, tonic::Status> {
        log::info!("meta() start");
        let mut result = OdsEnvCheckMetaResponse::default();
        Ok(Response::new(result))
    }

    async fn create(
        &self,
        request: tonic::Request<OdsEnvCheckRequest>,
    ) -> std::result::Result<tonic::Response<CommonResponse>, tonic::Status> {
        log::info!("create() start");
        let data = request.get_ref();
        let entity = OdsEnvCheckDtoUtil::from_dto(data.data.as_ref().unwrap());
        OdsEnvCheckService::create(&mut get_ds_connection() , entity);
        Ok(Response::new(CommonResponse::default()))
    }

    async fn update(
        &self,
        request: tonic::Request<OdsEnvCheckRequest>,
    ) -> std::result::Result<tonic::Response<CommonResponse>, tonic::Status> {
        log::info!("update() start");
        let data = request.get_ref();
        let entity = OdsEnvCheckDtoUtil::from_dto(data.data.as_ref().unwrap());
        OdsEnvCheckService::update_by_id(&mut get_ds_connection() , entity);
        Ok(Response::new(CommonResponse::default()))
    }

    async fn remove(
        &self,
        request: tonic::Request<NumberIdRequest>,
    ) -> std::result::Result<tonic::Response<CommonResponse>, tonic::Status> {
        log::info!("remove() start");
        let id = &request.get_ref().id;
        OdsEnvCheckService::delete_by_id(&mut get_ds_connection() , OdsEnvCheckId{id: id.clone()});
        Ok(Response::new(CommonResponse::default()))
    }

    async fn getone(
        &self,
        request: tonic::Request<NumberIdRequest>,
    ) -> std::result::Result<tonic::Response<OdsEnvCheckResponse>, tonic::Status> {
        log::info!("getone() start");
        let id = &request.get_ref().id;
        let result = OdsEnvCheckService::get_by_id(&mut get_ds_connection() , OdsEnvCheckId{id: id.clone()});
        let dto = OdsEnvCheckDtoUtil::to_dto(&result.unwrap());
        let mut r = OdsEnvCheckResponse::default();
        r.data = Some(dto);
        Ok(Response::new(r))
    }

    async fn query(
        &self,
        request: tonic::Request<OdsEnvCheckPageRequest>,
    ) -> std::result::Result<tonic::Response<OdsEnvCheckListResponse>, tonic::Status> {
        let request = request.get_ref().clone();
        let page_request = request.page_request.clone();
        let page_reqeust = page_request.expect("page_request is empty");
        let mut query = OdsEnvCheckQuery::default();
        query.page = page_reqeust.page;
        query.page_size = page_reqeust.page_size;
        query.keyword = page_reqeust.keyword;

        query.begin_create_time = toNativeDateTime(request.begin_create_time);
        query.end_create_time = toNativeDateTime(request.end_create_time);
        query.agv_id = request.agv_id;
        query.status = request.status;
        query.logic_x = request.logic_x;
        query.logic_y = request.logic_y;

        log::info!("query() start query:{:?}", query);

        let result = OdsEnvCheckService::query(&mut get_ds_connection(), &query);
        
        let (data_list, total_count) = &result.unwrap();
        let dto_data_list = OdsEnvCheckDtoUtil::to_list_dto(data_list);

        let mut response = OdsEnvCheckListResponse::default();
        response.common_response =  Some(CommonResponse::default());
        response.page_response = Some(PageResponse{total: total_count.clone(), page: query.page, page_size: query.page_size });
        response.data_list = dto_data_list;

        log::info!("query() end total_count:{:?}", total_count);
        Ok(Response::new(response))
    }
}

pub fn getOdsEnvCheckRpcServiceServer() -> OdsEnvCheckRpcServiceServer<OdsEnvCheckRpcServiceImpl> {
    OdsEnvCheckRpcServiceServer::new(OdsEnvCheckRpcServiceImpl {})
}



pub fn toNativeDateTime(time : Option<prost_types::Timestamp>) -> Option<NaiveDateTime> {
    if time.is_none() {
        return None;
    }
    let time = time.unwrap();
    return Some(NaiveDateTime::from_timestamp_opt(time.seconds, time.nanos as u32)
        .expect("Invalid timestamp"));

}