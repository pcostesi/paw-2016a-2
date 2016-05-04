<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="task" required="true" type="ar.edu.itba.models.Task" %>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="task-${task.taskId}" panelParent="${panelParent}">
	<jsp:attribute name="title">
		<span class="badge label-success">${task.status.label}</span> <span class="badge label-primary">${task.score.value}</span> 
		${task.title} (owned by ${task.owner})
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<a href="#" class="btn btn-xs btn-default">
			<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
		</a>
		<a href="#" class="btn btn-xs btn-danger">
			<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
		</a>
	<!-- <form action="/project/${project.code}/iteration/${iteration.iterationId}/story/${story.storyId}/task/${task.taskId}/delete" method="POST">
		<button type="submit" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  </form> -->
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-12">
            	<b>Description</b> ${task.description}<br>
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>