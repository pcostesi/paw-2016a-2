<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="iteration" required="true" type="ar.edu.itba.models.Iteration"%>
<%@attribute name="story" required="true" type="ar.edu.itba.models.Story"%>
<%@attribute name="tasks" required="true" type="java.util.List"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="story-${story.storyId()}" panelParent="${panelParent}">
	<jsp:attribute name="title">${story.title()}</jsp:attribute>
	
	<jsp:attribute name="actions">
	 	<a href="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}/task/new" class="btn btn-primary btn-xs dropdown-toggle">
		    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New task
		</a>
		<t:dropdownEditDelete href="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}"/>
	</jsp:attribute>
	
	<jsp:attribute name="list">
		<c:forEach items="${tasks}" var="task">		
			<li class="list-group-item"><t:taskPanel panelParent="#story-group-${story.storyId()}" project="${project}" iteration="${iteration}" story="${story}" task="${task}"/></li>
		</c:forEach>
	</jsp:attribute>	

	<jsp:body></jsp:body>
</t:collapsiblePanel>