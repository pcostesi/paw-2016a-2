<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="project.list.title"/>
    	
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <spring:message code="project.list.new_project"/>
	 	</a>
     </jsp:attribute>
        
     <jsp:body>
	     <c:choose>
			<c:when test="${projects.isEmpty()}">
				<div class="alert alert-info" role="alert">
					<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> <spring:message code="project.list.empty_list"/>
				</div>
			</c:when>    
			<c:otherwise>
				<c:forEach items="${projects}" var="project">
						<t:projectPanel project="${project}" user="${user}"/>
				</c:forEach>
			</c:otherwise>
		</c:choose>
    </jsp:body>    
</t:page>
