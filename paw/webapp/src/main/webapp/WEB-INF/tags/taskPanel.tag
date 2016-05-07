<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project" %>
<%@attribute name="iteration" required="true" type="ar.edu.itba.models.Iteration" %>
<%@attribute name="story" required="true" type="ar.edu.itba.models.Story" %>
<%@attribute name="task" required="true" type="ar.edu.itba.models.Task" %>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<<<<<<< 7929eda9f2de00a9df8b71f5078fa3238b35ef6a
<t:collapsiblePanel panelId="task-${task.taskId}" panelParent="${panelParent}">
	<jsp:attribute name="titleInfo">
		<span class="label label-default">${task.score.value}</span> <t:statusLabel taskStatus="${task.status}"/> 
	</jsp:attribute>

	<jsp:attribute name="title">
		${task.title}
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<t:dropdownEditDelete href="${pageContext.request.contextPath}/project/${project.code}/iteration/${iteration.iterationId}/story/${story.storyId}/task/${task.taskId}"/>
=======
<t:collapsiblePanel panelId="task-${task.taskId()}" panelParent="${panelParent}">
	<jsp:attribute name="title">Task: ${task.title()}</jsp:attribute>
	
	<jsp:attribute name="actions">
	<form action="/project/scrumlr/iteration/0/story/0/task/${task.taskId()}/delete" method="POST">
		<button type="submit" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  </form>
>>>>>>> Move to immutables
	</jsp:attribute>

	<jsp:body>
		<div class="row">
<<<<<<< 7929eda9f2de00a9df8b71f5078fa3238b35ef6a
            <div class="col-sm-12">
            	<c:choose>
				    <c:when test="${empty task.owner}">
				        <b>Owner</b> Task doesn't have an owner<br>
				    </c:when>    
				    <c:otherwise>
				        <b>Owner</b> ${task.owner.username()}<br>
				    </c:otherwise>
				</c:choose>            	
            	<b>Description</b> ${task.description}<br>
=======
            <div class="col-sm-8">
                ${task.description()}
                <jsp:doBody/>
            </div>
            <div class="col-sm-4">
                ${task.status().label}
>>>>>>> Move to immutables
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>