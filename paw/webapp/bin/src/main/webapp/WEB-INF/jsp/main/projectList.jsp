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
			<t:projectPanel project="scrumlr" panelParent="#project-list"/>
        </div>

    </jsp:body>    
</t:page>
