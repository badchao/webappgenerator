<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import { rpc } from '@/utils/request';

//增加数据处理或转换，再传输给后端
function convertSaveData(data) {
  return data;
}

export function query(params) {
    return rpc(`/${classNameLower}/query`,{
      method:'GET',
      params: params
    });
}

export function getone(params) {
    return rpc(`/${classNameLower}/getone`,{
      method:'GET',
      params: params,
    });
}


export function remove(params) {
    return rpc(`/${classNameLower}/remove`, {
        method: 'POST',
        requestType:'form',
        data: params,
    });
}

export function update(params) {
    return rpc(`/${classNameLower}/update`, {
        method: 'POST',
        requestType:'form',
        data: convertSaveData(params),
    });
}

export function create(params) {
    return rpc('/${classNameLower}/create', {
      method: 'POST',
      requestType:'form',
      data: convertSaveData(params),
    });
}




