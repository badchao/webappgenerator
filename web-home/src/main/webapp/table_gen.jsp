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
		<h1 class="text-center"><a class="btn btn-lg btn-success" href="${ctx}/project_gen.jsp">项目生成器</a> <a class="btn btn-lg btn-success" href="${ctx}/table_gen.jsp">表增删改查 - 代码生成器 </a></h1>
		<h1 class="text-center">表增删改查 - 代码生成器 , 联系:邱百超</h1>
	 	<form action="${ctx}/tablegenerator/gen.do" method="post" >
		 	<table width="100%">
		 		<tr>
		 			<td><b>create table SQL</b></td>
		 			<td><textarea class="form-control" id="sqls" name="sqls" cols="80" rows="19"  onchange="saveCookie()" placeholder="输入建表SQL,用分号；分隔多条SQL"></textarea>
		 				示例值: create table demo_table (id int AUTO_INCREMENT primary key, username varchar(20) ,age int,birth_date datetime);
		 			</td>
		 		</tr>
		 		<tr>
		 			<td><b>java package</b></td>
		 			<td><input class="form-control" type="text" id="basepackage" name="basepackage" placeholder="java包名" size="40" onchange="saveCookie()"/> 示例值:com.company.projectname</td>
		 		</tr>
		 		<tr>
		 			<td><b>projectId</b></td>
		 			<td><input class="form-control" type="text" id="projectId" name="projectId" placeholder="项目ID(项目代号),输入值:小写，全英文" size="40" onchange="saveCookie()"/></td>
		 		</tr>
				<tr>
		 			<td><b>web namespace</b></td>
		 			<td><input class="form-control" type="text" id="namespace" name="namespace" placeholder="示例值:admin,monitor,security" size="40" onchange="saveCookie()"/> web页面子模块,用于web分模块,会影响jsp存放位置,如/{namespace}/{className}/list.jsp,示例值:admin,monitor,security</td>
		 		</tr>
		 		<tr>
		 			<td><b>作者(author)</b></td>
		 			<td><input class="form-control" type="text" id="author" name="author" size="40" onchange="saveCookie()" placeholder="生成的代码会显示该作者"/></td>
		 		</tr> 
		 		<tr>
		 			<td><b>需要删除表名的前缀</b></td>
		 			<td><input class="form-control" type="text" id="tableRemovePrefixes" name="tableRemovePrefixes" size="40" onchange="saveCookie()" placeholder="生成的代码会删除该前缀，如t_user => user"/> 示例值: t_,v_</td>
		 		</tr> 
		 	</table>
		 	<div class="text-center"><input class="btn btn-primary btn-lg" type="submit" value="生成代码" /></div>
	 	</form>
	</div>
 	
<script type="text/javascript">

	function saveCookie() {
		localStorage.setItem('namespace', $('#namespace').val());
		localStorage.setItem('appModule', $('#appModule').val());
		localStorage.setItem('basepackage', $('#basepackage').val());
		localStorage.setItem('tableRemovePrefixes', $('#tableRemovePrefixes').val());
		localStorage.setItem('sqls', $('#sqls').val());
		localStorage.setItem('author', $('#author').val());
		localStorage.setItem('projectId', $('#projectId').val());
		
		/*
		$.cookie('namespace', $('#namespace').val(),{expires:10000000});
		$.cookie('appModule', $('#appModule').val(),{expires:10000000});
		$.cookie('basepackage', $('#basepackage').val(),{expires:10000000});
		$.cookie('tableRemovePrefixes', $('#tableRemovePrefixes').val(),{expires:10000000});
		$.cookie('sqls', $('#sqls').val(),{expires:10000000});
		$.cookie('author', $('#author').val(),{expires:10000000});
		*/
	}

	function recoverCookie() {
		$("#namespace").val(localStorage.getItem('namespace'));
		$("#appModule").val(localStorage.getItem('appModule'));
		$("#sqls").val(localStorage.getItem('sqls'));
		$("#basepackage").val(localStorage.getItem('basepackage'));
		$("#tableRemovePrefixes").val(localStorage.getItem('tableRemovePrefixes'));
		$("#author").val(localStorage.getItem('author'));
		$("#projectId").val(localStorage.getItem('projectId'));
		
		/*
		$("#namespace").val($.cookie('namespace'));
		$("#appModule").val($.cookie('appModule'));
		$("#sqls").val($.cookie('sqls'));
		$("#basepackage").val($.cookie('basepackage'));
		$("#tableRemovePrefixes").val($.cookie('tableRemovePrefixes'));
		$("#author").val($.cookie('author'));
		*/
	}

	recoverCookie();
	
</script>

</body>


</html>
