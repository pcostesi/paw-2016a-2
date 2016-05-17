<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<t:page>
	<jsp:attribute name="title">
    	<spring:message code="story.list.title">
    		<spring:argument>${project.name()}</spring:argument>
    		<spring:argument>${iteration.number()}</spring:argument>
    	</spring:message>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <spring:message code="story.list.new_story"/>
	 	</a>
     </jsp:attribute>

     <jsp:body>
     <div class="row">    
		<div class="col-sm-4">
			<div class="row">
				<div class="col-sm-12">
					<t:iterationDescriptionPanel iteration="${iteration}"></t:iterationDescriptionPanel>
				</div>
			</div>
			<div class="row">  
				<div class="col-sm-12">
					<t:backlogItemContainer items="${backlog}"></t:backlogItemContainer>
				</div>
			</div>
		</div>
		<div class="col-sm-8">
	     	<c:choose>
			    <c:when test="${stories.isEmpty()}">
			    	<div class="alert alert-info" role="alert">
						<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> <spring:message code="story.list.empty_list" />
					</div>
			    </c:when>    
			    <c:otherwise>
						<c:forEach items="${stories}" var="story">
								<t:storyPanel project="${project}" iteration="${iteration}" story="${story.key}" tasks="${story.value}"/>
						</c:forEach>
			    </c:otherwise>
			</c:choose>
		</div>
	</div>
    </jsp:body>    
</t:page>