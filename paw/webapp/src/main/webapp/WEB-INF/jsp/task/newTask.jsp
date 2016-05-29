<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
		<spring:message code="task.new.title" arguments="${project.name()},${iteration.number()}$" />
	</jsp:attribute>

	<jsp:body>
		<c:url value="/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}/task/new" var="formUrl" />
		<form:form modelAttribute="taskForm" action="${formUrl}" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldTitle"/>
						<form:hidden path="storyId" value="${story.storyId()}"/>
						<bs:input path="title" label="Title" />
						<bs:input path="description" label="Description" />
						<bs:select path="status" label="Task Status"/>
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
