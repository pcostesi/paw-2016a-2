<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="iteration" required="true" type="ar.edu.itba.models.Iteration" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="iteration-${iteration.number()}">
	<jsp:attribute name="titleInfo"> 
		<t:iterationStatusBadge status="${iteration.status()}"/> 
	</jsp:attribute>
	<jsp:attribute name="title">Iteration #${iteration.number()}</jsp:attribute>	
		<jsp:attribute name="actions">
			<c:url value="/project/${project.code()}/iteration/${iteration.iterationId()}" var="iterationUrl"/>
			<a href="${iterationUrl}" class="btn btn-default btn-xs" >
				<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> <spring:message code="iterationsPanel.iteration_link"/>
			</a>
			<t:dropdownEditDelete url="${iterationUrl}"/>
		</jsp:attribute>
		<jsp:body>
			<div class="row">
		        <div class="col-sm-12">
		            <p><strong><spring:message code="iterationsPanel.start_date"/></strong> ${iteration.startDate()}</p>
		            <p><strong><spring:message code="iterationsPanel.end_date"/></strong> ${iteration.endDate()}</p>
		        </div>
		    </div>
		</jsp:body>
</t:collapsiblePanel>
