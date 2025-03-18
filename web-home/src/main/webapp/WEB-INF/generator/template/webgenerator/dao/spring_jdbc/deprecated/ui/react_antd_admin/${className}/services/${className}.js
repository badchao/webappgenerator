<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import request from '@/utils/request';

export function query(params) {
    return request(`/${classNameLower}/query`,{
      method:'GET',
      params: params
    });
}

export function getone(params) {
    return request(`/${classNameLower}/getone`,{
      method:'GET',
      params: params,
    });
}


export function remove(params) {
    return request(`/${classNameLower}/remove`, {
        method: 'POST',
        requestType:'form',
        data: params,
    });
}

export function update(params) {
    return request(`/${classNameLower}/update`, {
        method: 'POST',
        requestType:'form',
        data: params,
    });
}

export function create(params) {
    return request('/${classNameLower}/create', {
      method: 'POST',
      requestType:'form',
      data: params,
    });
  }