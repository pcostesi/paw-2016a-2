<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Available Projects
    </jsp:attribute>
    
    <jsp:attribute name="actions">
	  <t:navbutton btnClass="btn-primary">
	    New Project
	  </t:navbutton>
     </jsp:attribute>
        
        
     <jsp:body>
		<div class="panel-group" id="project-list" role="tablist" aria-multiselectable="true">
			<c:forEach items="${projects}" var="project">
					<t:projectPanel panelParent="#project-list-${project.code}"  project="${project}" />
			</c:forEach>
        </div>

    </jsp:body>    
</t:page>
