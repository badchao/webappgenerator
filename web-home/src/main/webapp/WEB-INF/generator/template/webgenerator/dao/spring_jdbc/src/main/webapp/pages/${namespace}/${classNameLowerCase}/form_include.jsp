<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign dollar = '$'>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>


<#list table.columns as column>
<#if column.htmlHidden>
	<input name="${column.columnNameLower}" id="${column.columnNameLower}" value="<@jspEl classNameLower+"."+column.columnNameLower/>" type="hidden" />
</#if>
</#list>

<#list table.columns as column>
	<#if !column.htmlHidden>	
	<div class="form-group">
		<label for="${column.columnNameLower}" class="col-sm-3 control-label"><#if !column.nullable><span class="required">*</span></#if>${column.columnAlias}:</label>
		<div class="col-sm-6">
		<#if column.isDateTimeColumn>
			<input name="${column.columnNameLower}" id="${column.columnNameLower}" value='<fmt:formatDate value="<@jspEl classNameLower+"."+column.columnNameLower/>" pattern="yyyy-MM-dd"/>' class="form-control"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		<#else>
			<input name="${column.columnNameLower}" id="${column.columnNameLower}" value="${dollar}{${classNameLower}.${column.columnNameLower}}" placeholder="${column.columnAlias}"  maxlength="${column.size}"  class="form-control" ${GeneratorColumnUtil.getJqueryValidation(column)}/>
		</#if>
			<span class="help-block"></span>
			<span class="error form-control-feedback"><form:errors path="${column.columnNameLower}"/></span>
		</div>
	 </div>
	 
	</#if>
</#list>		