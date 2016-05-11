<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
    	New backlog item <small> for project ${project.name()}</small>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="backlogForm" action="${pageContext.request.contextPath}/project/${project.code()}/backlog/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="projectId" value="${project.projectId()}"/>
						<bs:input path="title" label="Title" />
						<bs:input path="description" label="Description" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
