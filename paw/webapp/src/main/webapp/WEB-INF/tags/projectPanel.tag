<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="project-${project}" panelParent="${panelParent}">
	<jsp:attribute name="title">Project ${project}</jsp:attribute>
	
	<jsp:attribute name="actions">
		<button type="button" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  <button type="button" class="btn btn-default btn-xs">
		    Edit
		  </button>
		  <a href="/project/${project}" class="btn btn-primary btn-xs">
		    Go to project
		  </a>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
	        <div class="col-sm-12">
	            ${description}
	        </div>
	    </div>
	    
	    <div class="row">
	        <div class="col-sm-12">
	            <a href="/project/${project}">Go to project</a>
	        </div>
	    </div>
	</jsp:body>
</t:collapsiblePanel>