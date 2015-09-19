<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代码生成器</title>



<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/jquery.cookie.js"></script>
<script>


</script>
</head>
<body>
	<h1>代码生成器,联系:邱百超</h1>
 	<form action="${ctx}/generator/gen.do" method="post" >
 	<table>
 		<tr>
 			<td>create table SQL(用分号；分隔多条SQL)</td>
 			<td><textarea id="sqls" name="sqls" cols="80" rows="20" onchange="saveCookie()"></textarea>
 				<br />示例值: create table demo_table (id int AUTO_INCREMENT primary key, username varchar(20) ,age int,birth_date datetime);
 			</td>
 		</tr>
 		<tr>
 			<td>java package</td>
 			<td><input type="text" id="basepackage" name="basepackage" size="40" onchange="saveCookie()"/> 示例值:com.company.projectname</td>
 		</tr>
 		<tr>
 			<td>需要删除表名的前缀</td>
 			<td><input type="text" id="tableRemovePrefixes" name="tableRemovePrefixes" size="40" onchange="saveCookie()"/> 示例值: t_,v_</td>
 		</tr> 		
		<tr>
 			<td>namespace</td>
 			<td><input type="text" id="namespace" name="namespace" size="40" onchange="saveCookie()"/> JSP页面划分的子模块,影响如/{namespace}/{className}/list.jsp</td>
 		</tr>
		<tr>
 			<td>appModule</td>
 			<td><input type="text" id="appModule" name="appModule" size="40" onchange="saveCookie()"/> 应用模块</td>
 		</tr> 		  		
 		<tr>
 			<td colspan="2" align="center"><input type="submit" value="生成代码"/> <input type="reset" value="清空表单" onclick="$('#consoleOutput').html('');setTimeout(function() {saveCookie()} ,500 );"/></td>
 		</tr>
 	</table>
 	</form>
 	
 	<div>
 		<pre id="consoleOutput"></pre>
 	</div>
 	
<script type="text/javascript">
	function saveCookie() {
		$.cookie('namespace', $('#namespace').val(),{expires:10000000});
		$.cookie('appModule', $('#appModule').val(),{expires:10000000});
		$.cookie('basepackage', $('#basepackage').val(),{expires:10000000});
		$.cookie('tableRemovePrefixes', $('#tableRemovePrefixes').val(),{expires:10000000});
		$.cookie('sqls', $('#sqls').val(),{expires:10000000});
		//alert("cookie:"+$.cookie('sqls')+" form:"+$('#sqls').val());
	}

	function recoverCookie() {
		$("#namespace").val($.cookie('namespace'));
		$("#appModule").val($.cookie('appModule'));
		$("#sqls").val($.cookie('sqls'));
		$("#basepackage").val($.cookie('basepackage'));
		$("#tableRemovePrefixes").val($.cookie('tableRemovePrefixes'));
	}

	recoverCookie();
	
</script>

</body>


</html>
