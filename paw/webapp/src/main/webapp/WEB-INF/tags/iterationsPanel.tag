<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project" %>
<%@attribute name="iterations" required="true" type="java.util.List" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
	<c:when test="${iterations.isEmpty()}">
		<div class="alert alert-info" role="alert">
			<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> <spring:message code="iterationsPanel.empty"/>
		</div>
	</c:when>    
	<c:otherwise>
		<c:forEach items="${iterations}" var="iteration">
				<t:iterationPanel iteration="${iteration}"/>
			</c:forEach>
	</c:otherwise>
</c:choose>
