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
	<script src="${dollar}{ctx}/js/app/${namespace}/${classNameLowerCase}/${className}Main.js" type="text/javascript"></script>
	
</rapid:override>


<rapid:override name="content">
	<div id="app"></div>
	
	<script type="text/javascript">
		var m = new ${className}Main();
		m.$mount('#app');
	</script>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

