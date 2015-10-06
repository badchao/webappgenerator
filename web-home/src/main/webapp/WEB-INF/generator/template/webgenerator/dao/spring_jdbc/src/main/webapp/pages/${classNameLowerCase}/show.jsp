<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title>${table.tableAlias} 信息</title>
</rapid:override>

<rapid:override name="content">
	<h2 id="title" class="text-center">查看 ${table.tableAlias}</h2>
	
	<form:form modelAttribute="${classNameLowerCase}" cssClass="form-horizontal"  >
		
		<#list table.columns as column>
		<#if !column.htmlHidden>
			<div class="row col-xs-12">
				<div class="col-xs-4 text-right"><b>${column.columnAlias}:</b></div>	
				<div class="col-xs-4">
				<#if column.isDateTimeColumn>
					<fmt:formatDate value='<@jspEl classNameLower+"."+column.columnNameLower/>' pattern="yyyy-MM-dd"/>
				<#else>
					<c:out value='<@jspEl classNameLower+"."+column.columnNameLower/>'/>
				</#if>
				</div>
			</div>
		</#if>
		</#list>
		
		<div class="text-center">
			<input type="button" class="btn btn-primary" value="返回列表" onclick="window.location='<@jspEl 'ctx'/>/${classNameLowerCase}/index.do'"/>&nbsp;&nbsp;&nbsp;
			<input type="button" class="btn btn-primary" value="后退" onclick="history.back();"/>
		</div>

	</form:form>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>