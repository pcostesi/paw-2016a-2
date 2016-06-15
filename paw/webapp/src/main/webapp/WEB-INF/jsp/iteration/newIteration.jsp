<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page>
	<jsp:attribute name="title">
		<spring:message code="iteration.new.title" arguments="${project.name()}" />
	</jsp:attribute>
	
	<jsp:body>
		<form:form id="iteration-form" modelAttribute="iterationForm" action="${pageContext.request.contextPath}/project/${project.code()}/iteration/new" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="projectId" value="${project.projectId()}"/>
						<form:hidden path="iterationId" value="-1"/>
						<bs:input id="beginDate" path="beginDate" label="Begin date" dateClass="dateInput" placeholder="dd/MM/YYYY"/>
						<bs:select path="duration" label="Duration" map="${durationOptions}"/>
						<bs:input id="endDate" path="endDate" label="End date" dateClass="dateInput" placeholder="dd/MM/YYYY"/>
						<c:if test="${not empty iterationToInheritFrom}">
							<bs:checkBox path="inheritIteration" label="Inherit unfinished tasks"/>
							<form:hidden path="iterationNumberToInherit" value="${iterationToInheritFrom}"/>
						</c:if>
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
