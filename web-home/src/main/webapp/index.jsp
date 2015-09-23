<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>web后台-代码生成器</title>


<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/jquery.cookie.js"></script>
<link href="${ctx}/styles/bootstrap.min.css" rel="stylesheet"  media="screen"/>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script>


</script>
</head>
<body>
	<div class="container">
		<h1 class="text-center">web后台代码生成器 , 联系:邱百超</h1>
	 	<form action="${ctx}/generator/gen.do" method="post" >
		 	<table width="100%">
		 		<tr>
		 			<td><b>create table SQL(用分号；分隔多条SQL)</b></td>
		 			<td><textarea class="form-control" id="sqls" name="sqls" cols="80" rows="19"  onchange="saveCookie()"></textarea>
		 				示例值: create table demo_table (id int AUTO_INCREMENT primary key, username varchar(20) ,age int,birth_date datetime);
		 			</td>
		 		</tr>
		 		<tr>
		 			<td><b>java package</b></td>
		 			<td><input class="form-control" type="text" id="basepackage" name="basepackage" placeholder="com.company.projectname" size="40" onchange="saveCookie()"/> 示例值:com.company.projectname</td>
		 		</tr>
		 		<tr>
		 			<td><b>需要删除表名的前缀</b></td>
		 			<td><input class="form-control" type="text" id="tableRemovePrefixes" name="tableRemovePrefixes" size="40" onchange="saveCookie()"/> 示例值: t_,v_</td>
		 		</tr> 		
				<tr>
		 			<td><b>namespace</b></td>
		 			<td><input class="form-control" type="text" id="namespace" name="namespace" placeholder="示例值:monitor,security" size="40" onchange="saveCookie()"/> JSP页面划分的子模块,影响如/{namespace}/{className}/list.jsp,示例值:monitor,security</td>
		 		</tr>
		 	</table>
		 	<div class="text-center"><input class="btn btn-primary btn-lg" type="submit" value="生成代码" /></div>
	 	</form>
	</div>
 	
<script type="text/javascript">
	function saveCookie() {
		$.cookie('namespace', $('#namespace').val(),{expires:10000000});
		$.cookie('appModule', $('#appModule').val(),{expires:10000000});
		$.cookie('basepackage', $('#basepackage').val(),{expires:10000000});
		$.cookie('tableRemovePrefixes', $('#tableRemovePrefixes').val(),{expires:10000000});
		$.cookie('sqls', $('#sqls').val(),{expires:10000000});
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
