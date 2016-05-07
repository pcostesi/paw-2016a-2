<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>
	<jsp:attribute name="title">
    	New Iteration <small>Project ${project.name()}</small>
	</jsp:attribute>
	
	<jsp:body>
<<<<<<< 7929eda9f2de00a9df8b71f5078fa3238b35ef6a
		<form:form modelAttribute="iterationForm" action="${pageContext.request.contextPath}/project/${project.code}/iteration/new" method="POST">
=======
		<form:form modelAttribute="iterationForm" action="/project/${project.code()}/iteration/new" method="POST">
>>>>>>> Move to immutables
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="beginDate" label="Begin date" />
						<bs:input path="endDate" label="End date" />
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
