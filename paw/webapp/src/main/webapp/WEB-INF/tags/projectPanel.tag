<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="project-${project.code()}">
	<jsp:attribute name="title">${project.name()}</jsp:attribute>	
	<jsp:attribute name="actions">
<<<<<<< 7929eda9f2de00a9df8b71f5078fa3238b35ef6a
		<a href="${pageContext.request.contextPath}/project/${project.code}" class="btn btn-default btn-xs">
				<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> Go to project
		</a>
		<t:dropdownEditDelete href="${pageContext.request.contextPath}/project/${project.code}"/>
=======
		<form action="/project/${project.code()}" method="GET">
		<button type="submit" class="btn btn-default btn-xs">
			<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> Go to project
		</button>
		</form>
		<form action="/project/${project.code()}/edit" method="GET">
			<button type="submit" class="btn btn-xs btn-default">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
			</button>
		</form>	
		<form action="/project/${project.code()}/delete" method="POST">
			<button type="submit" class="btn btn-xs btn-danger">
				<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
			</button>
		</form>		  
>>>>>>> Move to immutables
	</jsp:attribute>
	<jsp:body>
		<div class="row">
	        <div class="col-sm-12">
	        	<b>Start date</b> ${project.startDate()}<br>
	            <b>Description</b> ${project.description()}
	        </div>
	    </div>
	</jsp:body>
</t:collapsiblePanel>