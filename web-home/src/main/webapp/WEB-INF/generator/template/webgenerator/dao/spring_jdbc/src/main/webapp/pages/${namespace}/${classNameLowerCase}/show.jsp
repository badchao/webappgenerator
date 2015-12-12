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
			<div class="row form-group">
				<label class="col-sm-4 control-label">${column.columnAlias}:</label>	
				<div class="col-sm-4">
				<#if column.isDateTimeColumn>
					<fmt:formatDate value='<@jspEl classNameLower+"."+column.columnNameLower/>' pattern="yyyy-MM-dd"/>
				<#else>
					<c:out value='<@jspEl classNameLower+"."+column.columnNameLower/>'/>
				</#if>
				</div>
			</div>
		</#list>
		
		<div class="text-center">
			<a class="btn btn-primary" href="<@jspEl "ctx"/>${classWebBasePath}/index.do">返回列表</a>&nbsp;&nbsp;&nbsp;
			<input type="button" class="btn btn-primary" value="后退" onclick="history.back();"/>
		</div>

	</form:form>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>