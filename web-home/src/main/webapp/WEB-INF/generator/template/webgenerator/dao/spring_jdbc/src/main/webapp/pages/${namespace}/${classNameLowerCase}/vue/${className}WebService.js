<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
var baseWsPath="/rpc"

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

var ${className}WebService = {

	findPage : function(query,success,error) {
		var q = ${className}Util.convertQuery(query);
		$.getJSON(baseWsPath+"/${className}WebService/findPage",q,AppUtil.wsCallback(success,error));
	},
	
	removeById : function(<@generatePassingParameters table.pkColumns/>,success,error) {
		var pk = {
		<#list table.pkColumns as column>
			${column.columnNameLower} : ${column.columnNameLower}<#if column_has_next>,</#if>
		</#list>
		};
		$.post(baseWsPath+"/${className}WebService/removeById",pk,AppUtil.wsCallback(success,error),"json");
	},
	
	create : function(${classNameFirstLower},success,error) {
		var entity = ${className}Util.convertEntity(${classNameFirstLower});
		$.post(baseWsPath+"/${className}WebService/create", entity,AppUtil.wsCallback(success,error),"json");
	},
	
	update : function(${classNameFirstLower},success,error) {
		var entity = ${className}Util.convertEntity(${classNameFirstLower});
		$.post(baseWsPath+"/${className}WebService/update", entity,AppUtil.wsCallback(success,error),"json");
	}
	
};