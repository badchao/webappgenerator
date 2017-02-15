<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>

/**
 * webservice for ${className}
 * author: ${author}
 */

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