<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import { rpc } from '@/utils/request';

export function findPage(params) {
    return rpc(`/${classNameLower}/findPage`,{
      method:'GET',
      params: params
    });
}

export function getById(params) {
    return rpc(`/${classNameLower}/getById`,{
      method:'GET',
      params: params,
    });
}


export function remove(params) {
    return rpc(`/${classNameLower}/removeById`, {
        method: 'POST',
        requestType:'form',
        data: params,
    });
}

export function update(params) {
    return rpc(`/${classNameLower}/update`, {
        method: 'POST',
        requestType:'form',
        data: params,
    });
}

export function create(params) {
    return rpc('/${classNameLower}/create', {
      method: 'POST',
      requestType:'form',
      data: params,
    });
}


