<%@ include file="/commons/taglibs.jsp" %>

<%-- Error Messages --%>
<c:if test="${flash.success != null}">
	<div class="alert alert-success">
		${flash.success}<br/>
	</div>    
</c:if>

<%-- Info Messages --%>
<c:if test="${flash.error != null}">
	<div class="alert alert-danger">
		${flash.error}<br/>
	</div> 
</c:if>
