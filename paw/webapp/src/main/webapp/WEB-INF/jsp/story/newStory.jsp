<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/project/${project.code()}/iteration/${iteration.iterationId()}/story/new" var="formUrl" />

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="story.new.title">
    		<spring:argument>${project.name()}</spring:argument>
    		<spring:argument>${iteration.number()}</spring:argument>
    	</spring:message>
	</jsp:attribute>

	<jsp:body>
		<form:form modelAttribute="storyForm" action="${formUrl}" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldTitle"/>
						<form:hidden path="iterationId" value="${iteration.iterationId()}"/>
						<bs:input path="title" label="Title" />
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
