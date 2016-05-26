<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:staticPanel panelId="${project.code()}-description">
	<jsp:attribute name="title"><spring:message code="descriptionPanel.title"/></jsp:attribute>
	<jsp:body>
		<p><strong><spring:message code="descriptionPanel.start_date"/></strong> ${project.startDate()}</p>
	    <p><strong><spring:message code="descriptionPanel.description"/></strong> ${project.description()}</p>
	</jsp:body>
</t:staticPanel>