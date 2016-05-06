<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="project-${project.code}">
	<jsp:attribute name="title">${project.name}</jsp:attribute>	
	<jsp:attribute name="actions">
		<a href="${pageContext.servletContext}/project/${project.code}" class="btn btn-default btn-xs">
				<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> Go to project
		</a>
		<t:dropdownEditDelete href="${pageContext.servletContext}/project/${project.code}"/>
	</jsp:attribute>
	<jsp:body>
		<div class="row">
	        <div class="col-sm-12">
	        	<b>Start date</b> ${project.startDate}<br>
	            <b>Description</b> ${project.description}
	        </div>
	    </div>
	</jsp:body>
</t:collapsiblePanel>