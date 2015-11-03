<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign dollar = '$'>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>


<#list table.columns as column>
<#if column.htmlHidden>
	<input type="hidden" id="${column.columnNameLower}" name="${column.columnNameLower}" value="<@jspEl classNameLower+"."+column.columnNameLower/>"/>
</#if>
</#list>

<#list table.columns as column>
	<#if !column.htmlHidden>	
	<div class="form-group">
		<label for="${column.columnNameLower}" class="col-md-4 control-label"><#if !column.nullable><span class="required">*</span></#if>${column.columnAlias}:</label>
		<div class="col-md-4">
		<#if column.isDateTimeColumn>
			<input value='<fmt:formatDate value="<@jspEl classNameLower+"."+column.columnNameLower/>" pattern="yyyy-MM-dd"/>' class="form-control"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}" name="${column.columnNameLower}"  maxlength="0" />
		<#else>
			<input value="${dollar}{${classNameLower}.${column.columnNameLower}}" name="${column.columnNameLower}" id="${column.columnNameLower}" class="form-control" maxlength="${column.size}" placeholder="${column.columnNameLower}" ${GeneratorColumnUtil.getJqueryValidation(column)}/>
		</#if>
			<span class="help-block"></span>
			<span class="error"><form:errors path="${column.columnNameLower}"/></span>
		</div>
	 </div>
	 
	</#if>
</#list>		