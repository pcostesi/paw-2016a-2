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

	<jsp:body>		
        <c:choose>
	        <c:when test="${tasks.isEmpty()}">
	    		This story doesn't have any task so far
		    </c:when>    
		    <c:otherwise>
		    	<div class="panel-group" id="story-group-${story.storyId()}" role="tablist" aria-multiselectable="true">
			    	<c:forEach items="${tasks}" var="task">		
						<t:taskPanel panelParent="#story-group-${story.storyId()}" project="${project}" iteration="${iteration}" story="${story}" task="${task}"/>	
					</c:forEach>	
				</div>
		    </c:otherwise>	
        </c:choose>					
	</jsp:body>
</t:collapsiblePanel>