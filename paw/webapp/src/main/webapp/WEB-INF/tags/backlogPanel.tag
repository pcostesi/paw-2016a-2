<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project" %>
<%@attribute name="backlog" required="true" type="java.util.List<ar.edu.itba.models.BacklogItem>" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:url value="/project/${project.code()}/backlog/new" var="newUrl" />

<t:staticPanel panelId="task-${task.taskId()}">

	<jsp:attribute name="title">
		Backlog
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<a class="btn btn-default btn-xs" href="${newUrl}">Add new</a>
	</jsp:attribute>
		
	<jsp:attribute name="list">
		<c:forEach items="${backlog}" var="item">
			<li class="list-group-item">
				<t:backlogItemPanel project="${project}" item="${item}"/>
			</li>
		</c:forEach>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-12">
            	This list contains the backlog for this project.
            </div>
        </div>
	</jsp:body>
	
	
</t:staticPanel>