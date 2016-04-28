<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Iteration #${iteration} <small>for Project Scrumlr</small>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
	  <t:navbutton btnClass="btn-primary">
	    New Story
	  </t:navbutton>
     </jsp:attribute>
        
        
     <jsp:body>     	
        <div class="panel-group" id="story-list" role="tablist" aria-multiselectable="true">
			<c:forEach items="${stories}" var="story">
					<t:storyPanel panelParent="#story-list" story="${story}" />
			</c:forEach>
		</div>
    </jsp:body>    
</t:page>