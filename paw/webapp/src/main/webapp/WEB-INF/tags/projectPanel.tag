<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="project-${project.code}" panelParent="${panelParent}">
	<jsp:attribute name="title">Project ${project.name}</jsp:attribute>
	
	<jsp:attribute name="actions">
		<button type="button" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  <button type="button" class="btn btn-default btn-xs">
		    Edit
		  </button>
		  <a href="/project/${project.code}" class="btn btn-primary btn-xs">
		    Go to project
		  </a>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
	        <div class="col-sm-12">
	            ${project.description}
	        </div>
	    </div>
	</jsp:body>
</t:collapsiblePanel>