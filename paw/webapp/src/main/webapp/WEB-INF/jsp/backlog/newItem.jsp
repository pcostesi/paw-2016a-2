<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="backlog.newItem.title.placeholder" var="changeTitlePlaceholder"/> 
<spring:message code=backlog.newItem.description.placeholder" var="descriptionPLaceholder"/>

<t:page>
	<jsp:attribute name="title">
		<spring:message code="item.new.title" arguments="${project.name()}" />
	</jsp:attribute>

	<jsp:body>
		<c:url value="/project/${project.code()}/backlog/new" var="formUrl"/>
		<form:form modelAttribute="backlogForm" action="${formUrl}" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="projectId" value="${project.projectId()}"/>
						<bs:input path="title" label="Title" placeholder="${changeTitlePlaceholder}"/>
						<bs:input path="description" label="Description" placeholder="${descriptionPLaceholder}"/>
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
