<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>

/**
 * vue Controller for ${className}
 * author: ${author}
 */

var ${className}Controler = {
	  data: {
	    query : {
	    	pageSize : 10,
	    	page : 1
	    },
	    itemList : [],
	    paginator : {},
	    
	    ${classNameFirstLower} : {},
	    formErrors : {}
	  },
	  mounted : function() {
		  this.query = AppUtil.getJsonFromSessionStorage("${classNameFirstLower}Query",this.query);
		  this.findPage();
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
		  
		  findPage : function() {
			  var _this = this;
			  
			  ${className}WebService.findPage(_this.query,function(response) {
				  _this.itemList = response.result.itemList;
				  _this.paginator = response.result.paginator;
				  if(sessionStorage) sessionStorage.setItem("${classNameFirstLower}Query", JSON.stringify(_this.query));
			  });
		  },
		  
		  createForm : function(${classNameFirstLower}) {
			  var _this = this;
			  
			  this.formErrors = {};
			  this.${classNameFirstLower} = {};
			  ${className}Util.formatEntity(this.${classNameFirstLower});
			  
			  $('#createFormDialog').modal('show');
		  },
		  
		  editForm : function(${classNameFirstLower}) {
			  var _this = this;
			  
			  this.formErrors = {};
			  this.${classNameFirstLower} = ${classNameFirstLower};
			  ${className}Util.formatEntity(this.${classNameFirstLower});
			  
			  $('#editFormDialog').modal('show');
		  },
		  
		  create : function() {
			  var _this = this;
			  var validator = $("#createForm").validate();
			  if(!validator.form()) {
				  return;
			  }
				  
			  this.formErrors = ${className}Util.checkEntity(this.${classNameFirstLower});
			  if(AppUtil.hasProps(this.formErrors)) {
				  return;
			  }
			  
			  ${className}WebService.create(_this.${classNameFirstLower},function(response) {
				  _this.findPage();
				  $('#createFormDialog').modal('hide');
			  },this._saveErrorHandler);		  
		  },
		  
		  update : function() {
			  var _this = this;
			  var validator = $("#editForm").validate();
			  if(!validator.form()) {
				  return;
			  }
			  
			  this.formErrors = ${className}Util.checkEntity(this.${classNameFirstLower});
			  if(AppUtil.hasProps(this.formErrors)) {
				  return;
			  }
			  
			  ${className}WebService.update(_this.${classNameFirstLower},function(response) {
				  _this.findPage();
				  $('#editFormDialog').modal('hide');
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
	  }// methods() end
	  
};