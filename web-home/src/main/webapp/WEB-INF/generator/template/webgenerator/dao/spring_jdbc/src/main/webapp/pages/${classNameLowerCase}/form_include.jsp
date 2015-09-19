<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
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
		<label for="${column.columnNameLower}" class="col-sm-2 control-label"><#if !column.nullable><span class="required">*</span></#if>${column.columnAlias}:</label>
		<div class="col-sm-10">
		<#if column.isDateTimeColumn>
			<input value='<fmt:formatDate value="<@jspEl classNameLower+"."+column.columnNameLower/>" pattern="yyyy-MM-dd"/>' class="form-control"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}" name="${column.columnNameLower}"  maxlength="0" class="${column.validateString}" />
		<#else>
			<form:input path="${column.columnNameLower}" id="${column.columnNameLower}" cssClass="form-control ${column.validateString}" maxlength="${column.size}" placeholder="${column.columnNameLower}"/>
		</#if>
			<font color='red'><form:errors path="${column.columnNameLower}"/></font>
		</div>
	 </div>
	
	</#if>
</#list>		