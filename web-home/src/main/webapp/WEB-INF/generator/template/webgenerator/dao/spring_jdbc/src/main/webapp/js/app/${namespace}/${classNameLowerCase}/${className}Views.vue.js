<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
"use strict";
/**
 * vue views component for ${className}
 * author: ${author}
 */
Vue.component('${classNameLowerCase}-table', {
  props: ['rows'],
  methods : {
	  fireEditEvent : function(data) {
		  this.$emit('edit',data);
	  },
	  fireRemoveEvent : function(data) {
		  this.$emit('remove',data);
	  }
  },
  template: multiline(function() {
	  /*!@preserve
	  <table class='table table-hover scrolltable sortable'>
	  <thead>
		  <tr>
			<#list table.columns as column>
			<th sortColumn="${column.sqlName}" >${column.columnAlias}</th>
			</#list>
			<th>操作</th>
		  </tr>
	  </thead>
	  <tbody>
	  	  
		  <tr v-for='row in rows'>
			<#list table.columns as column>
				<td><#rt>
					<#compress>
					<#if column.isDateTimeColumn>
					{{row.${column.columnNameLower} | formatDate('YYYY-MM-DD') }}
					<#else>
					{{row.${column.columnNameLower}}}
					</#if>
					</#compress>
				<#lt></td>
			</#list>
				<td>
					<a class='btn btn-primary btn-xs' @click='fireEditEvent(row)'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span> 修改</a>&nbsp;&nbsp;
					<a class='btn btn-danger btn-xs'  @click='fireRemoveEvent(row)' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span> 删除</a>
				</td>
		  </tr>
		  
	  </tbody>
	</table>
  */})
});
		
Vue.component('${classNameLowerCase}-form', {
  props: ['id','${classNameLowerCase}','formAction','formErrors'],
  template: multiline(function() {
	/*!@preserve
  	<form :id='id' class='form-horizontal'>
    
	<#list table.columns as column>
	<#if column.htmlHidden>
		<input name="${column.columnNameLower}" id="${column.columnNameLower}" type="hidden" v-model='${classNameLowerCase}.${column.columnNameLower}'/>
	</#if>
	</#list>

	<#list table.columns as column>
		<#if !column.htmlHidden>	
		<div class="form-group">
			<label for="${column.columnNameLower}" class="col-sm-3 control-label"><#if !column.nullable><span class="required">*</span></#if>${column.columnAlias}:</label>
			<div class="col-sm-6">
			<#if column.isDateTimeColumn>
				<input name="${column.columnNameLower}" id="${column.columnNameLower}" v-model.lazy='${classNameLowerCase}.${column.columnNameLower}' class="form-control"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange='' />
			<#else>
				<input name="${column.columnNameLower}" id="${column.columnNameLower}" v-model.trim='${classNameLowerCase}.${column.columnNameLower}' placeholder=""  maxlength="${column.size}"  class="form-control" ${GeneratorColumnUtil.getJqueryValidation(column)}/>
			</#if>
				<span class="help-block"></span>
				<span class="error form-control-feedback">{{formErrors.${column.columnNameLower}}}</span>
			</div>
		 </div>
		 
		</#if>
	</#list>
    
	 </form>
  */})
});
		
Vue.component('${classNameLowerCase}-query-form',{
	props : ['query'],
	template: multiline(function() {
	/*!@preserve
	<div id="query-content" class="row visible-lg">
		<#list table.columns as column>
			
		<#if !column.htmlHidden>
		<#if column.isDateTimeColumn>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="${column.columnNameLower}Begin" class="control-label">开始${column.columnAlias}</label>
				<input name="${column.columnNameLower}Begin" id="${column.columnNameLower}Begin"  v-model.lazy='query.${column.columnNameLower}Begin' placeholder="开始时间"  class="form-control input-from-control"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange=''  />
			</div>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="${column.columnNameLower}End" class="control-label">结束${column.columnAlias}</label>
				<input name="${column.columnNameLower}End" id="${column.columnNameLower}End" v-model.lazy='query.${column.columnNameLower}End' placeholder="结束时间" class="form-control input-from-control"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange=''   />
			</div>
		</div>
		<#else>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="${column.columnNameLower}" class="control-label">${column.columnAlias}</label>
				<input name="${column.columnNameLower}" id="${column.columnNameLower}" v-model='query.${column.columnNameLower}' placeholder=""  class="form-control input-from-control"   maxlength="${column.size}" />
			</div>
		</div>
		</#if>
		</#if>
		</#list>
	</div>
	*/})
	
});
		