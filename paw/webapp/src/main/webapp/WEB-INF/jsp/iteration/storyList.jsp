<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
<<<<<<< 7929eda9f2de00a9df8b71f5078fa3238b35ef6a
    	Iteration #${iteration.number} <small>Project ${project.name}</small>
=======
    	Iteration #${iteration.number()} <small>for Project Scrumlr</small>
>>>>>>> Move to immutables
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/${project.code}/iteration/${iteration.iterationId}/story/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New story
	 	</a>
     </jsp:attribute>

     <jsp:body>     	
        <div class="panel-group" id="story-list" role="tablist" aria-multiselectable="true">
			<c:forEach items="${stories}" var="story">
					<t:storyPanel panelParent="#story-list" project="${project}" iteration="${iteration}" story="${story.key}" tasks="${story.value}"/>
			</c:forEach>
		</div>
    </jsp:body>    
</t:page>