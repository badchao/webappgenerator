<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<#assign dollor = '$'> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title>上传${table.tableAlias}</title>
</rapid:override>

<rapid:override name="content">
	<h2 id="title" class="text-center">上传${table.tableAlias}</h2>
	
	<form class="form-horizontal" action="${dollor}{ctx}/${classNameLowerCase}/upload.do" method="post"  enctype="multipart/form-data" >
		  
		<div class="form-group">
			<label for="username" class="col-md-4 control-label">文件</label>
			<div class="col-md-4">
				<input type="file" name="file" class="file" placeholder="选择上传文件"/>
				<span class="help-block"><a href="${dollor}{ctx}/pages/${classNameLowerCase}/upload_${className}.csv" >下载数据上传模板</a></span>
			</div>
		</div>
	 
		<div class="form-group">
		    <div class="text-center">
				<input id="submitButton" class="btn btn-success" name="submitButton" type="submit" value="上传" />&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn btn-primary" value="返回列表" onclick="window.location='${dollor}{ctx}/${classNameLowerCase}/index.do'"/>&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn btn-primary" value="后退" onclick="history.back();"/>
		    </div>
		</div>
	</form>
	
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>