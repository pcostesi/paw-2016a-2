<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="iteration.list.title" arguments="${project.name()}"/>
    </jsp:attribute>
     
    <jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/${project.code()}/members" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-user" aria-hidden="true"></span> <spring:message code="iteration.list.members"/>
	 	</a>
	    <a href="${pageContext.request.contextPath}/project/${project.code()}/iteration/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <spring:message code="iteration.list.new_iteration"/>
		</a>
     </jsp:attribute>
     
     <jsp:body>
		<div class="row">
			<div class="col-sm-4">
				<div class="row">
			     	<div class="col-sm-12">
						<t:descriptionPanel project="${project}"/>
					</div>
				</div>
				<div class="row">
			     	<div class="col-sm-12">
						<t:backlogItemContainer items="${backlog}"></t:backlogItemContainer>
					</div>
				</div>				
			</div>
			<div class="col-sm-8">
				<t:iterationsPanel project="${project}" iterations="${iterations}"/>
			</div>
		</div>
    </jsp:body>    
</t:page>    
</body>
</html>