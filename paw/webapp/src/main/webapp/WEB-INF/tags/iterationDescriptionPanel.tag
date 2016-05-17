<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="iteration" required="true" type="ar.edu.itba.models.Iteration"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:staticPanel panelId="${iteration.iterationId()}-description">
	<jsp:attribute name="title"><spring:message code="iterationDescription.title"/></jsp:attribute>
	<jsp:body>
		<p><strong><spring:message code="iterationDescription.start_date"/></strong> ${iteration.formattedStartDate()}</p>
	    <p><strong><spring:message code="iterationDescription.end_date"/></strong> ${iteration.formattedEndDate()}</p>
	</jsp:body>
</t:staticPanel>