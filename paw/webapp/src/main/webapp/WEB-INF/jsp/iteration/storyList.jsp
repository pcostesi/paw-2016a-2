<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Iteration #${iteration.number} <small>Project ${project.name}</small>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="/grupo2/project/${project.code}/iteration/${iteration.iterationId}/story/new" class="btn btn-primary btn-sm">
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