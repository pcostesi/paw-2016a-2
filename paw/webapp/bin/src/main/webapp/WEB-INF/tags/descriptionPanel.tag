<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:staticPanel panelId="${project.code}-description">
	<jsp:attribute name="title">Project description</jsp:attribute>
	<jsp:body>
		<b>Start date</b> ${project.startDate}<br>
	    <b>Description</b> ${project.description}
	</jsp:body>
</t:staticPanel>