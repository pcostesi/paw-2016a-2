<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="iteration" required="true" type="ar.edu.itba.models.Iteration"%>
<%@attribute name="story" required="true" type="ar.edu.itba.models.Story"%>
<%@attribute name="tasks" required="true" type="java.util.List"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="story-${story.storyId()}">
	<jsp:attribute name="title">${story.title()}</jsp:attribute>
	
	<jsp:attribute name="actions">
	 	<a href="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}/task/new" class="btn btn-primary btn-xs dropdown-toggle">
		    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New task
		</a>
		<t:dropdownEditDelete url="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}"/>
	</jsp:attribute>

	<jsp:attribute name="list"><c:if test="${not items.isEmpty()}">	
		<c:forEach items="${tasks}" var="task">	
			<li class="list-group-item">	
				<t:taskPanel panelParent="#story-group-${story.storyId()}" project="${project}" iteration="${iteration}" story="${story}" task="${task}"/>	
			</li>
		</c:forEach>	 		
	</c:if></jsp:attribute>

	<jsp:body><c:if test="${tasks.isEmpty()}">
		<div class="row">
            <div class="col-sm-12">
				    This story doesn't have any task so far
            </div>
        </div>		
	</c:if></jsp:body>
	
</t:collapsiblePanel>