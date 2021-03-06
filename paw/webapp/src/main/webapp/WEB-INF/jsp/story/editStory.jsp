<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="bs" tagdir="/WEB-INF/tags/bs" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:message code="story.edi.title.placeholder" var="storyNamePlaceholder"/>

<t:page>
	<jsp:attribute name="title">
    	<spring:message code="story.edit.title"/>
	</jsp:attribute>	
	
	<jsp:body>
		<c:url value="/project/${project.code()}/iteration/${iteration.iterationId()}/story/${story.storyId()}/edit" var="formUrl"/>
		<form:form modelAttribute="storyForm" action="${formUrl}" method="POST">
			<div class="row">
				<div class="col-sm-6">
					<fieldset>
						<form:hidden path="oldTitle" value="${story.title()}"/>
						<form:hidden path="iterationId" value="${iteration.iterationId()}"/>
						<bs:input path="title" label="Title" placeholder="${storyNamePlaceholder}"/>
					</fieldset>
				</div>
			</div>
			<bs:submit/>

		</form:form>
    </jsp:body>
</t:page>
