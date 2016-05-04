<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="story" required="true" type="ar.edu.itba.models.Story"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="story-${story.storyId}" panelParent="${panelParent}">
	<jsp:attribute name="title">${story.title}</jsp:attribute>
	
	<jsp:attribute name="actions">
		<button type="button" class="btn btn-default btn-xs">
		    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
		  </button>
		<button type="button" class="btn btn-xs btn-danger">
			<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
		</button>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-12">
            	<a href="/project/${project.code}/iteration/${iteration.number}/story/${story.storyId}">Go to story</a>
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>