<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title>${table.tableAlias}编辑</title>
</rapid:override>

<rapid:override name="content">
	<h2 id="title" class="text-center">编辑${table.tableAlias}</h2>
	
	<form:form id="inputForm" method="put" cssClass="form-horizontal" action="<@jspEl 'ctx'/>/${classNameLowerCase}/update.do" modelAttribute="${classNameFirstLower}">
			
		<%@ include file="form_include.jsp" %>
		
		<div class="form-group">
		    <div class="text-center">
				<input id="submitButton" class="btn btn-success" name="submitButton" type="submit" value="提交" />&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn btn-primary" value="返回列表" onclick="window.location='<@jspEl "ctx"/>/${classNameLowerCase}/index.do'"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn btn-primary" value="后退" onclick="history.back();"/>
		    </div>
		</div>
		
	</form:form>
	
	<script>
		$("#inputForm").validate();
	</script>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>