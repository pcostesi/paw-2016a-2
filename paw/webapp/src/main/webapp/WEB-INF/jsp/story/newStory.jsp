<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
    	Add a new story <small>to Project ${project.name} (Iteration #${iteration.number})</small>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="storyForm" action="${pageContext.request.contextPath}/project/${project.code}/iteration/${iteration.iterationId}/story/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldTitle" value="${story.title}"/>
						<form:hidden path="iterationId" value="${iteration.iterationId}"/>
						<bs:input path="title" label="Title" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
