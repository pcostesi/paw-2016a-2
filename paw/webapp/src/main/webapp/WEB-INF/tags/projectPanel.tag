<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:url value="/project/${project.code()}" var="projectUrl"/>

<t:collapsiblePanel panelId="project-${project.code()}">
	<jsp:attribute name="title">${project.name()}</jsp:attribute>	
	<jsp:attribute name="actions">
		<a href="${projectUrl}" class="btn btn-default btn-xs">
				<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> <spring:message code="projectPanel.project_link"/>
		</a>
		<t:dropdownEditDelete url="${projectUrl}"/>
	</jsp:attribute>
	<jsp:body>
		<div class="row">
	        <div class="col-sm-12">
<<<<<<< HEAD
	        	<b>Start date</b> ${project.startDate()}<br>
	            <b>Description</b> ${project.description()}
=======
	        	<p><strong><spring:message code="projectPanel.start_date"/></strong> ${project.formattedStartDate()}</p>
	            <p><strong><spring:message code="projectPanel.description"/></strong> ${project.description()}</p>
>>>>>>> master
	        </div>
	    </div>
	</jsp:body>
</t:collapsiblePanel>