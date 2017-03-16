<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
"use strict";
/**
 * vue main view for ${className}
 * author: ${author}
 */
var ${className}Main = Vue.extend({
	  template: multiline(function() {/*!@preserve
		<div id="${classNameLowerCase}-main">
			<form id="queryForm" name="queryForm" method="get" @submit="findPage" onsubmit="return false;" >
				
				<div class="panel panel-default">
					<div class="panel-heading">${table.tableAlias} 列表</div>
					<div class="panel-body">
						<${classNameLowerCase}-query-form :query="query"></${classNameLowerCase}-query-form>
						<div class="row text-left">
							<div class="col-sm-5">
								<a href="#" class="btn btn-primary btn-sm hidden-lg" onclick="$('#query-content').toggleClass('visible-lg');">显示搜索</a>
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
			
			<submit-dialog id="${classNameFirstLower}CreateFormDialog" title="新增${table.tableAlias}" @submit="create" submit-text="保存" >
				<${classNameLowerCase}-form id="${classNameFirstLower}CreateForm" :${classNameLowerCase}="${classNameFirstLower}" :form-errors="formErrors" form-action="create"   ></${classNameLowerCase}-form>
			</submit-dialog>
			
			<submit-dialog id="${classNameFirstLower}EditFormDialog" title="编辑${table.tableAlias}" @submit="update" submit-text="保存" >
				<${classNameLowerCase}-form id="${classNameFirstLower}EditForm" :${classNameLowerCase}="${classNameFirstLower}" :form-errors="formErrors" form-action="edit"   ></${classNameLowerCase}-form>
			</submit-dialog>
		</div>  
	  */}),
	  data: function() {
		  return {
		    query : {
		    	pageSize : 10,
		    	page : 1
		    },
		    rows : [],
		    paginator : {},
		    
		    ${classNameFirstLower} : {},
		    formErrors : {}
		  }
	  },
	  mounted : function() {
		  this.query = AppUtil.getJsonFromSessionStorage("${classNameFirstLower}Query",this.query);
		  this.index();
	  },
	  methods : {
		  changePageSize : function(pageSize) {
			  this.query.pageSize = pageSize;
			  this.findPage();
		  },
		  
		  changePage : function(page) {
			  this.query.page = page;
			  this.findPage();
		  },
		  
		  index : function() {
			  $('.submit-dialog').modal('hide');
			  this.findPage();
		  },
		  
		  findPage : function() {
			  var _this = this;
			  
			  ${className}WebService.findPage(_this.query,function(response) {
				  _this.rows = response.result.itemList;
				  _this.paginator = response.result.paginator;
				  if(sessionStorage) sessionStorage.setItem("${classNameFirstLower}Query", JSON.stringify(_this.query));
			  });
		  },
		  
		  createForm : function(${classNameFirstLower}) {
			  var _this = this;
			  
			  this.formErrors = {};
			  this.${classNameFirstLower} = {};
			  ${className}Util.formatEntity(this.${classNameFirstLower});
			  
			  $('#${classNameFirstLower}CreateFormDialog').modal('show');
		  },
		  
		  editForm : function(${classNameFirstLower}) {
			  var _this = this;
			  
			  this.formErrors = {};
			  this.${classNameFirstLower} = ${classNameFirstLower};
			  ${className}Util.formatEntity(this.${classNameFirstLower});
			  
			  $('#${classNameFirstLower}EditFormDialog').modal('show');
		  },
		  
		  create : function() {
			  var _this = this;
			  var validator = $("#${classNameFirstLower}CreateForm").validate();
			  if(!validator.form()) {
				  return;
			  }
				  
			  this.formErrors = ${className}Util.checkEntity(this.${classNameFirstLower});
			  if(AppUtil.hasProps(this.formErrors)) {
				  return;
			  }
			  
			  ${className}WebService.create(_this.${classNameFirstLower},function(response) {
				  _this.index();
			  },this._saveErrorHandler);		  
		  },
		  
		  update : function() {
			  var _this = this;
			  var validator = $("#${classNameFirstLower}EditForm").validate();
			  if(!validator.form()) {
				  return;
			  }
			  
			  this.formErrors = ${className}Util.checkEntity(this.${classNameFirstLower});
			  if(AppUtil.hasProps(this.formErrors)) {
				  return;
			  }
			  
			  ${className}WebService.update(_this.${classNameFirstLower},function(response) {
				  _this.index();
			  },this._saveErrorHandler);		  
		  },
		  
		  remove : function(${classNameFirstLower}) {
			  var _this = this;
			  if(confirm("确认删除?")) {
				 <#list table.pkColumns as column>
				 var ${column.columnNameLower} = ${classNameFirstLower}.${column.columnNameLower};
				 </#list>
			 	 ${className}WebService.removeById(<@generatePassingParameters table.pkColumns/>,function(response) {
			 		_this.findPage();
			 	 });
			  }
		  },
		  
		  _saveErrorHandler : function(response) {
			  if(response.errCode = 'ConstraintViolationException') {
				  alert("数据 输入有问题");
			  }else {
				  alert("错误信息:"+response.errMsg);
			  }
		  }
	  }
	  
});