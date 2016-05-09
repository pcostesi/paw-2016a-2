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
		<form:form modelAttribute="iterationForm" action="${pageContext.request.contextPath}/project/${project.code()}/iteration/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<bs:input path="beginDate" label="Begin date" dateClass="dateInput"/>
						<bs:input path="endDate" label="End date" dateClass="dateInput"/>
					</fieldset>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form:form>
    </jsp:body>
</t:page>
