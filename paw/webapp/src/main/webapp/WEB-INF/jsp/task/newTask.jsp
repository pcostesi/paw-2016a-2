<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
    	Add a new task <small>to Project Scrumlr &mdash; iteration #1</small>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="taskForm" action="/project/scrumlr/iteration/1/task/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="title" label="Title" />
						<bs:input path="description" label="Description" />
						<bs:select path="status" label="Task Status" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
