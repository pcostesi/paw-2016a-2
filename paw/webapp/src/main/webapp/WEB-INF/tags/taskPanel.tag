<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="task" required="true" type="ar.edu.itba.models.Task" %>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="task-${task.taskId}" panelParent="${panelParent}">
	<jsp:attribute name="title">Task: ${task.title}</jsp:attribute>
	
	<jsp:attribute name="actions">
		<button type="button" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  <button type="button" class="btn btn-default btn-xs">
		    Edit
		  </button>
		  <button type="button" class="btn btn-xs btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Started <span class="caret"></span>
		  </button>
		  <ul class="dropdown-menu">
		    <li><a href="#">Pending</a></li>
		    <li><a href="#">Started</a></li>
		    <li><a href="#">Blocked</a></li>
		    <li><a href="#">Done</a></li>
		  </ul>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-8">
                ${task.description}
                <jsp:doBody/>
            </div>
            <div class="col-sm-4">
                ${task.status}
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>