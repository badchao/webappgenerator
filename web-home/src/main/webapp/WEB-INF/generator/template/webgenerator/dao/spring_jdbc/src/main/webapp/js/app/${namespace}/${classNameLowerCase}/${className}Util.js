<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
"use strict";
/**
 * util for ${className}
 * author: ${author}
 */
var ${className}Util = {
	/** 
	 * select2 component config
	 * use example: <select2 v-model='someEntity.${table.pkColumn.columnNameLower}' :config="${className}Util.select2Config" />
	 **/
	select2Config : {
		ajax: {
			url: globalWsUrl+'/${className}WebService/findPage?page=1&pageSize=999&__format=jsonp',
			cache: true,
			dataType: 'jsonp',
			jsonp: "__jsoncallback",
			processResults: function (data, params){
				var r = AppUtil.toSelect2Options(data.result.itemList,'${table.pkColumn.columnNameLower}','${table.pkColumn.columnNameLower}');
				return {results : r}; 
			}
		} 
	},
		
	convertQuery : function(query) {
		var r = $.extend({},query);
		<#list table.columns as column>
			<#if column.isDateTimeColumn>
		r.${column.columnNameLower}Begin = AppUtil.toTimestamp(r.${column.columnNameLower}Begin,"YYYY-MM-DD");
		r.${column.columnNameLower}End = AppUtil.toTimestamp(r.${column.columnNameLower}End,"YYYY-MM-DD");
			</#if>
		</#list>
		return r;
	},
	
	convertEntity : function(entity) {
		var r = $.extend({},entity);
		<#list table.columns as column>
			<#if column.isDateTimeColumn>
		r.${column.columnNameLower} = AppUtil.toTimestamp(r.${column.columnNameLower},"YYYY-MM-DD");
			</#if>
		</#list>
		return r;
	},
	
	formatEntity : function(entity) {
		<#list table.columns as column>
			<#if column.isDateTimeColumn>
		entity.${column.columnNameLower} = AppUtil.formatDate(entity.${column.columnNameLower},'YYYY-MM-DD');
			</#if>
		</#list>
	},
	
	checkEntity : function(${classNameFirstLower}) {
		  var formErrors = {};
		  <#list table.notPkColumns as column>
		  <#if !column.nullable>
		  if(!${classNameFirstLower}.${column.columnNameLower}) {
			  formErrors.${column.columnNameLower} = '不能为空';
		  }
		  </#if>
		  </#list>
		  return formErrors;
	}
	
}