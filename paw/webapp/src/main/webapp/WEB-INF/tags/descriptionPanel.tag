<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:staticPanel panelId="${project.code()}-description">
	<jsp:attribute name="title"><spring:message code="descriptionPanel.title"/></jsp:attribute>
	<jsp:body>
<<<<<<< HEAD
		<b>Start date</b> ${project.startDate()}<br>
	    <b>Description</b> ${project.description()}
=======
		<p><strong><spring:message code="descriptionPanel.start_date"/></strong> ${project.formattedStartDate()}</p>
	    <p><strong><spring:message code="descriptionPanel.description"/></strong> ${project.description()}</p>
>>>>>>> master
	</jsp:body>
</t:staticPanel>