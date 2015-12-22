<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLowerCase = className?lower_case>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title>${table.tableAlias} 列表</title>
	
	<script src="<@jspEl 'ctx'/>/js/rest.js" ></script>
	<link href="<c:url value="/widgets/simpletable/simpletable.css"/>" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="<c:url value="/widgets/simpletable/simpletable.js"/>"></script>
	
	<script type="text/javascript" >
		$(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('queryForm',<@jspEl 'page.paginator.page'/>,<@jspEl 'page.paginator.pageSize'/>,'<@jspEl 'pageRequest.sortColumns'/>');
		});
	</script>
</rapid:override>


<rapid:override name="content">
	<form id="queryForm" name="queryForm" method="get" >
	
	<div class="panel panel-default">
	
		<div class="panel-heading">${table.tableAlias} 列表</div>
		<div class="panel-body">
		<#list table.columns?chunk(4) as row>
			
			<div class="row">
			<#list row as column>
			<#if !column.htmlHidden>
				<div class="col-sm-3">
					<div class="form-group">
						<#if column.isDateTimeColumn>
						<label>开始${column.columnAlias}</label>
						<input class="form-control input-from-control" placeholder="开始时间" value="<fmt:formatDate value='<@jspEl "query."+column.columnNameLower+'Begin'/>' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}Begin" name="${column.columnNameLower}Begin"   />
						<label>结束${column.columnAlias}</label>
						<input class="form-control input-from-control" placeholder="结束时间" value="<fmt:formatDate value='<@jspEl "query."+column.columnNameLower+'End'/>' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="${column.columnNameLower}End" name="${column.columnNameLower}End"   />
						<#else>
						<label>${column.columnAlias}</label>
						<input class="form-control input-from-control" placeholder="${column.columnAlias}" value="<@jspEl "query."+column.columnNameLower/>" id="${column.columnNameLower}" name="${column.columnNameLower}" maxlength="${column.size}"  class="${column.noRequiredValidateString}"/>
						</#if>
					</div>
				</div>
			</#if>
			</#list>
			</div>

		</#list>	
				
			<div style="margin-top:20px"  class="row text-left">
				<div class="col-sm-5">
					<a href="#" class="btn btn-primary btn-sm"  onclick="getReferenceForm(this).action='<@jspEl 'ctx'/>${classWebBasePath}/index.do'; $(getReferenceForm(this)).submit();return false;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 搜索</a>
					<a class="btn btn-primary btn-sm" href="<@jspEl 'ctx'/>${classWebBasePath}/add.do"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增</a>
					<a class="btn btn-primary btn-sm" href="<@jspEl 'ctx'/>/pages${classWebBasePath}/upload.jsp"><span class="glyphicon glyphicon-import" aria-hidden="true"></span> 批量导入</a>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel panel-default table-responsive">
		
		<table class="table table-striped table-bordered table-hover table-condensed  scrolltable sortable">
		  <thead>
			  <tr>
				<th style="width:1px;"> </th>
				<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
				<#list table.columns as column>
				<th sortColumn="${column.sqlName}" >${column.columnAlias}</th>
				</#list>
				<th>操作</th>
			  </tr>
		  </thead>
		  <tbody>
		  	  <c:forEach items="<@jspEl 'page.itemList'/>" var="item" varStatus="status">
		  	  
			  <tr>
				<td><@jspEl 'page.paginator.startRow + status.index'/></td>
				
				<#list table.columns as column>
				<td><#rt>
					<#compress>
					<#if column.isDateTimeColumn>
					<fmt:formatDate value='<@jspEl "item."+column.columnNameLower/>' pattern='yyyy-MM-dd'/>&nbsp;
					<#else>
					<c:out value='<@jspEl "item."+column.columnNameLower/>'/>&nbsp;
					</#if>
					</#compress>
				<#lt></td>
				</#list>
				
				<td>
					<a class="btn btn-primary btn-xs" href="<@jspEl 'ctx'/>${classWebBasePath}/show.do?<@generateHtmlLinkArguments table.pkColumns/>"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 查看</a>&nbsp;&nbsp;
					<a class="btn btn-primary btn-xs" href="<@jspEl 'ctx'/>${classWebBasePath}/edit.do?<@generateHtmlLinkArguments table.pkColumns/>"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改</a>&nbsp;&nbsp;
					<a class="btn btn-danger btn-xs" href="<@jspEl 'ctx'/>${classWebBasePath}/delete.do?<@generateHtmlLinkArguments table.pkColumns/>" onclick="doRestDelete(this,'你确认删除?');return false;"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除</a>
				</td>
			  </tr>
			  
		  	  </c:forEach>
		  </tbody>
		</table>
	
		<simpletable:pageToolbar paginator="<@jspEl 'page.paginator'/>">
		显示在这里是为了提示你如何自定义表头,可修改模板删除此行
		</simpletable:pageToolbar>
		
	</div>
	
	</form>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

<#macro generateHtmlLinkArguments columns>
<#compress>
<#list columns as column>${column.columnNameFirstLower}=<@jspEl 'item.'+column.columnNameFirstLower/><#if column_has_next>&</#if></#list>
</#compress>
</#macro>