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
			<div class="panel panel-default">
                <div class="panel-heading" role="tab" id="project-heading-${idx}">
                    <h2 class="panel-title">
                        <a role="button" data-toggle="collapse" data-parent="#project-list" href="#project-collapse-${idx}" aria-expanded="true" aria-controls="project-collapse-${idx}">
                            Project Scrumlr
                        </a>
                    </h2>
                </div>
                <div id="project-collapse-${idx}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="project-heading-${idx}">

                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-12">
                                ${description}
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-sm-12">
                                <a href="/project/scrumlr">Go to project</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- /panel -->
        </div>

    </jsp:body>    
</t:page>
