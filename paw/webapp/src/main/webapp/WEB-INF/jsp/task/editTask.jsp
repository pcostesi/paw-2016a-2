<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="task.edit.title"/>
	</jsp:attribute>

	<jsp:body>
		<c:url value="/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}/task/${task.taskId()}/edit" var="formUrl" />
		<form:form modelAttribute="taskForm" action="${formUrl}" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldTitle" value="${task.title()}"/>
						<form:hidden path="storyId" value="${story.storyId()}"/>
						<bs:input path="title" label="Title" placeholder="Vaccumm the room"/>
						<bs:input path="description" label="Description" placeholder="The room is nasty"/>
						<bs:select path="status" label="Task Status" />
						<bs:select path="score" label="Task Score" />
						<bs:select path="priority" label="Task Priority" />
						<bs:filledSelect path="owner" label="Task Owner" items="${users}" nullOption="None"/>
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
