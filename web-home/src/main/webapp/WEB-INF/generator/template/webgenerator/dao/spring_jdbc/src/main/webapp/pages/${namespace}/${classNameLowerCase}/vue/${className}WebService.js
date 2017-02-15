<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>

var ${className}Util = {
	
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

var globalWsUrl = globalWsUrl ? globalWsUrl : '/rpc';
var ${classNameFirstLower}WsUrl = globalWsUrl + "/${className}WebService";
var ${className}WebService = {

	findPage : function(query,success,error) {
		var q = ${className}Util.convertQuery(query);
		$.jsonp(${classNameFirstLower}WsUrl+"/findPage",q,AppUtil.wsCallback(success,error));
	},
	
	getById : function(<@generatePassingParameters table.pkColumns/>,success,error) {
		var pk = <#compress>{<#list table.pkColumns as column>${column.columnNameLower} : ${column.columnNameLower}<#if column_has_next>,</#if></#list>};</#compress>
		$.jsonp(${classNameFirstLower}WsUrl+"/getById",pk,AppUtil.wsCallback(success,error));
	},
	
	removeById : function(<@generatePassingParameters table.pkColumns/>,success,error) {
		var pk = <#compress>{<#list table.pkColumns as column>${column.columnNameLower} : ${column.columnNameLower}<#if column_has_next>,</#if></#list>};</#compress>
		$.jsonp(${classNameFirstLower}WsUrl+"/removeById",pk,AppUtil.wsCallback(success,error));
	},
	
	create : function(${classNameFirstLower},success,error) {
		var entity = ${className}Util.convertEntity(${classNameFirstLower});
		$.jsonp(${classNameFirstLower}WsUrl+"/create", entity,AppUtil.wsCallback(success,error));
	},
	
	update : function(${classNameFirstLower},success,error) {
		var entity = ${className}Util.convertEntity(${classNameFirstLower});
		$.jsonp(${classNameFirstLower}WsUrl+"/update", entity,AppUtil.wsCallback(success,error));
	}
	
};