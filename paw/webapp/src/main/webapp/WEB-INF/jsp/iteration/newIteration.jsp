<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	New Iteration<small>For project ${project.name}</small>
	</jsp:attribute>
	
	<jsp:body>
		<form:form modelAttribute="projectForm" action="/project/${project.code}/iteration/new" method="POST">
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
