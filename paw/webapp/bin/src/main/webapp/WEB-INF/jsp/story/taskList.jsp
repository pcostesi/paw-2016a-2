<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Story <small>${story.title}</small>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
	  <t:navbutton btnClass="btn-primary">
	    <a href="/project/scrumlr/iteration/0/story/0/task/new">New Task</a>
	  </t:navbutton>
     </jsp:attribute>
        
        
     <jsp:body>     	
        <div class="panel-group" id="task-list" role="tablist" aria-multiselectable="true">
			<c:forEach items="${tasks}" var="task">
					<t:taskPanel panelParent="#task-list" task="${task}" />
			</c:forEach>
		</div>
    </jsp:body>    
</t:page>