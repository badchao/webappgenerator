<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>

import request from '@/utils/request'

var ${className}Client = {
	create: function(params) {
		return request({
		    url: '/admin/${classNameLowerCase}/create',
		    method: 'post',
		    params: params
		  })
	},
	removeById: function(params) {
		return request({
		    url: '/admin/${classNameLowerCase}/removeById',
		    method: 'post',
		    params: params
		  })
	},
	update: function(params) {
		return request({
		    url: '/admin/${classNameLowerCase}/update',
		    method: 'post',
		    params: params
		  })
	},
	getById: function(params) {
		return request({
		    url: '/admin/${classNameLowerCase}/getById',
		    method: 'get',
		    params: params
		  })
	},
	findPage: function(params) {
		return request({
		    url: '/admin/${classNameLowerCase}/findPage',
		    method: 'get',
		    params: params
		  })
	},
}

var CheckRules = {
	<#list table.columns as column>
	${column.columnNameLower}: [{ required: false, message: '请输入${column.columnAlias}' }],
	</#list>
}


export {
	${className}Client,
	CheckRules,
};


