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

	<script src="${dollar}{ctx}/js/app/${namespace}/${classNameLowerCase}/${className}Util.js" type="text/javascript"></script>
	<script src="${dollar}{ctx}/js/app/${namespace}/${classNameLowerCase}/${className}Views.vue.js" type="text/javascript"></script>
	<script src="${dollar}{ctx}/js/app/${namespace}/${classNameLowerCase}/${className}WebService.js" type="text/javascript"></script>
	<script src="${dollar}{ctx}/js/app/${namespace}/${classNameLowerCase}/${className}Controller.js" type="text/javascript"></script>
	
</rapid:override>


<rapid:override name="content">
	<div id="${classNameLowerCase}-main">
		<form id="queryForm" name="queryForm" method="get" @submit="findPage" onsubmit="return false;" >
			
			<div class="panel panel-default">
				<div class="panel-heading">${table.tableAlias} 列表</div>
				<div class="panel-body">
					<${classNameLowerCase}-query-form :query="query"></${classNameLowerCase}-query-form>
					<div style="margin-top:20px"  class="row text-left">
						<div class="col-sm-5">
							<button id="search-button" type="submit" class="btn btn-primary btn-sm"  ><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 搜索</button>
							<a id="create-button"  class="btn btn-primary btn-sm" @click="createForm" ><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增</a>
						</div>
					</div>
				</div>
			</div>
			
		</form>
			
		<div class="panel panel-default table-responsive">
			<${classNameLowerCase}-table :rows="rows" @edit="editForm" @remove="remove"></${classNameLowerCase}-table>
			<simplepagination @changepage="changePage" @changepagesize="changePageSize" :paginator="paginator"></simplepagination>
		</div>
		
		<submit-dialog id="createFormDialog" title="新增${table.tableAlias}" @submit="create" submit-text="保存" >
			<${classNameLowerCase}-form id="createForm" :${classNameLowerCase}="${classNameFirstLower}" :form-errors="formErrors" form-action="create"   ></${classNameLowerCase}-form>
		</submit-dialog>
		
		<submit-dialog id="editFormDialog" title="编辑${table.tableAlias}" @submit="update" submit-text="保存" >
			<${classNameLowerCase}-form id="editForm" :${classNameLowerCase}="${classNameFirstLower}" :form-errors="formErrors" form-action="edit"   ></${classNameLowerCase}-form>
		</submit-dialog>
			
	</div>
	
	<script type="text/javascript">
		var ${className}Vue = new Vue($.extend({el:'#${classNameLowerCase}-main'},${className}Controler));
	</script>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

