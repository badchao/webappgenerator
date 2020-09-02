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
		<h1 class="text-center">项目生成器 , 联系:邱百超</h1>
		
	 	<form action="${ctx}/projectgenerator/gen.do" method="post" >
		 	<table width="100%">
		 		<tr>
		 			<td><b><span class="required">*</span>项目模板</b></td>
		 			<td>
		 				<select class="form-control"  name="archetypeGroupIdArtifactId">
		 					<option value="com.github.rapid:rapid-vue-springboot-springjdbc-archetype">vue + springboot + springjdbc模板</option>
<!-- 		 					<option value="org.apache.maven.archetypes:maven-archetype-quickstart">maven-archetype-quickstart示例模板</option> -->
		 				</select>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td><b><span class="required">*</span>projectId</b></td>
		 			<td><input class="form-control" type="text" id="projectId" name="projectId" placeholder="项目ID(项目代号),输入值:小写，全英文" size="40" onchange="saveData()"/> 示例值:demoproject</td>
		 		</tr>
		 		<tr>
		 			<td><b><span class="required">*</span>java package</b></td>
		 			<td><input class="form-control" type="text" id="basepackage" name="basepackage" placeholder="java包名" size="40" onchange="saveData()"/> 示例值:com.company.projectname</td>
		 		</tr>
		 	</table>
		 	<div class="text-center"><input class="btn btn-primary btn-lg" type="submit" value="生成项目并下载" /></div>
	 	</form>
	 	
	</div>
 	
<script type="text/javascript">

	function saveData() {
		localStorage.setItem('basepackage', $('#basepackage').val());
		localStorage.setItem('projectId', $('#projectId').val());
	}

	function recoverData() {
		//$("#basepackage").val(localStorage.getItem('basepackage'));
		//$("#projectId").val(localStorage.getItem('projectId'));
	}

	recoverData();
	
</script>

<style type="text/css">
  .required {
    color:red;
    font-weight: bold;
    font-size: 16pt;
  }
</style>

</body>


</html>
