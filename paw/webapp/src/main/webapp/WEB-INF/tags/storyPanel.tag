<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="story" required="true" type="ar.edu.itba.models.Story"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="story-${story.storyId}" panelParent="${panelParent}">
	<jsp:attribute name="title">Story: ${story.title}</jsp:attribute>
	
	<jsp:attribute name="actions">
		<button type="button" class="btn btn-xs btn-danger">
			Delete
		  </button>
		  <button type="button" class="btn btn-default btn-xs">
		    Edit
		  </button>
		  <button type="button" class="btn btn-xs btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Started <span class="caret"></span>
		  </button>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-8">
            	<a href="/project/scrumlr/iteration/0/story/${story.storyId}">Go to story</a>
            </div>
            <div class="col-sm-4">
            	We might add some details here...	
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>