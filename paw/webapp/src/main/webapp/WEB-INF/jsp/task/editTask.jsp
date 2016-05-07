<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<

<t:page>
	<jsp:attribute name="title">
    	Edit Task
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="taskForm" action="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}/task/${task.taskId()}/edit" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="title" label="Title" />
						<bs:input path="description" label="Description" />
						<bs:select path="status" label="Task Status" />
						<bs:select path="score" label="Task Score" />
						<bs:filledSelect path="owner" label="Task Owner" items="${users}"/>
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
