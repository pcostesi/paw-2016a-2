<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
    	Edit project ${project.name()}
	</jsp:attribute>
	
	<jsp:body>
<<<<<<< 7929eda9f2de00a9df8b71f5078fa3238b35ef6a
		<form:form modelAttribute="projectForm" action="${pageContext.request.contextPath}/project/${project.code}/edit" method="POST">
=======
		<form:form modelAttribute="projectForm" action="/project/${project.code()}/edit" method="POST">
>>>>>>> Move to immutables
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="name" label="Name" />
						<bs:input path="code" label="Code" />
						<bs:input path="description" label="Description" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
