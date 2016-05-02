<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="project-${project.code}">
	<jsp:attribute name="title">${project.name}</jsp:attribute>	
	<jsp:attribute name="actions">
		<a href="/project/${project.code}" class="btn btn-default btn-xs">
			<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> Go to project
		</a>
		<a href="#" class="btn btn-default btn-xs">
		  <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
		</a>
		<a href="#" class="btn btn-danger btn-xs">
		  <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
		</a>		  
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