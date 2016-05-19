<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="iteration" required="true" type="ar.edu.itba.models.Iteration"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:staticPanel panelId="${iteration.iterationId()}-description">
	<jsp:attribute name="title">Iteration details</jsp:attribute>
	<jsp:body>
		<b>Start date</b> ${iteration.startDate()}<br>
	    <b>End date:</b> ${iteration.endDate()}
	</jsp:body>
</t:staticPanel>