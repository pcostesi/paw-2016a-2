<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="task" required="true" type="ar.edu.itba.models.Task" %>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="task-${task.taskId}" panelParent="${panelParent}">
	<jsp:attribute name="title">Task: ${task.title}</jsp:attribute>
	
	<jsp:attribute name="actions">
	<form action="/project/scrumlr/iteration/0/story/0/task/${task.taskId}/delete" method="POST">
		<button type="submit" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  </form>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-8">
                ${task.description}
                <jsp:doBody/>
            </div>
            <div class="col-sm-4">
                ${task.status.label}
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>