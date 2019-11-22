<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>

import request from '@/utils/request'

var ${className}Client = {
	create: function(data) {
		return request({
		    url: '/${classNameLowerCase}/create',
		    method: 'post',
		    params: data
		  })
	},
	removeById: function(query) {
		return request({
		    url: '/${classNameLowerCase}/removeById',
		    method: 'post',
		    params: query
		  })
	},
	update: function(data) {
		return request({
		    url: '/${classNameLowerCase}/update',
		    method: 'post',
		    params: data
		  })
	},
	getById: function(query) {
		return request({
		    url: '/${classNameLowerCase}/getById',
		    method: 'get',
		    params: query
		  })
	},
	findPage: function(query) {
		return request({
		    url: '/${classNameLowerCase}/findPage',
		    method: 'get',
		    params: query
		  })
	},
}

var CheckRules = {
	<#list table.columns as column>
	${column.columnNameLower}: [{ required: false, message: '请输入${column.columnAlias}', trigger: 'blur' }],
	</#list>
}

export ${className}Client;




