<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign dollar = '$'>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title>${table.tableAlias} 列表</title>

	<script src="${className}Views.vue.js" type="text/javascript"></script>
	<script src="${className}WebService.js" type="text/javascript"></script>
	
</rapid:override>


<rapid:override name="content">
	<div id="${classNameFirstLower}-main">
		<form id="queryForm" name="queryForm" method="get" @submit="findPage" onsubmit="return false;" >
			
			<div class="panel panel-default">
				<div class="panel-heading">${table.tableAlias} 列表</div>
				<div class="panel-body">
					<${classNameFirstLower}-query-form :query="query"></${classNameFirstLower}-query-form>
					<div style="margin-top:20px"  class="row text-left">
						<div class="col-sm-5">
							<button type="submit" class="btn btn-primary btn-sm" ><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 搜索</button>
							<a class="btn btn-primary btn-sm" @click="createForm"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增</a>
						</div>
					</div>
				</div>
			</div>
			
			<div class="panel panel-default table-responsive">
				<${classNameFirstLower}-table :rows="itemList" @edit="editForm" @remove="remove"></${classNameFirstLower}-table>
				<simplepagination @changepage="changePage" @changepagesize="changePageSize" :paginator="paginator"></simplepagination>
			</div>
			
		</form>
		
		<submit-dialog id="createFormDialog" title="新增${table.tableAlias}" @submit="create" submit-text="保存" >
			<${classNameFirstLower}-form id="createForm" :${classNameFirstLower}="${classNameFirstLower}" :form-errors="formErrors" form-action="create"   />
		</submit-dialog>
		
		<submit-dialog id="editFormDialog" title="编辑${table.tableAlias}" @submit="update" submit-text="保存" >
			<${classNameFirstLower}-form id="editForm" :${classNameFirstLower}="${classNameFirstLower}" :form-errors="formErrors" form-action="edit"   />
		</submit-dialog>
			
	</div>
	
	<script src="${className}Controller.js" type="text/javascript"></script>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

