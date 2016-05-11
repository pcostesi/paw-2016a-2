<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Available Projects
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New project
	 	</a>
     </jsp:attribute>
        
        
     <jsp:body>
	     <c:choose>
			<c:when test="${projects.isEmpty()}">
				<div class="alert alert-info" role="alert">
					<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> There aren't any projects so far
				</div>
			</c:when>    
			<c:otherwise>
				<c:forEach items="${projects}" var="project">
						<t:projectPanel project="${project}" />
				</c:forEach>
			</c:otherwise>
		</c:choose>
    </jsp:body>    
</t:page>
