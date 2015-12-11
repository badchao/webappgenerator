<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign dollor = '$'>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<!-- 生成html select标签，应用场景：表之前有外键关联，如主从表，用于生成主从select标签,用于form表单的输入,需配合 jsp:include 标签使用 -->
<select class="form-control input-from-control" id="select_${dollor}{selectName}" name="${dollor}{selectName}" multiple="multiple">
	<c:forEach	var="item" items="${dollor}{itemList}">
		<option value="${dollor}{item.${table.pkColumn.columnNameFirstLower}}">${dollor}{item.${table.pkColumn.columnNameFirstLower}}</option>
	</c:forEach>
</select>
<script>
	$("#select_${dollor}{selectName}").multipleSelect({
	    filter: true,
	    single: true
	});
</script>