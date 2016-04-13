<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Iteration #1 <small>for Project Scrumlr</small>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
	  <t:navbutton btnClass="btn-danger">
	    Delete
	  </t:navbutton>
	  <t:navbutton btnClass="btn-default">
	    Edit
	  </t:navbutton>
	  <t:navbutton btnClass="btn-primary">
	    New Task
	  </t:navbutton>
     </jsp:attribute>
        
        
     <jsp:body>
        <div class="panel-group" id="tasks-list" role="tablist" aria-multiselectable="true">

			<div class="panel panel-default">
                <div class="panel-heading" role="tab" id="task-heading-${idx}">
                    <h2 class="panel-title clearfix">
                        <a role="button" data-toggle="collapse" data-parent="#task-list" href="#task-collapse-${idx}" aria-expanded="true" aria-controls="task-collapse-${idx}">
                            Task name
                        </a>

						<div class="btn-group pull-right">
						  <button type="button" class="btn btn-xs btn-danger">
						    Delete
						  </button>
						  <button type="button" class="btn btn-default btn-xs">
						    Edit
						  </button>
						  <button type="button" class="btn btn-xs btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    Started <span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu">
						    <li><a href="#">Pending</a></li>
						    <li><a href="#">Started</a></li>
						    <li><a href="#">Done</a></li>
						  </ul>
						</div>
                        
                    </h2>
                </div>
                <div id="task-collapse-${idx}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="task-heading-${idx}">

                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-8">
                                ${description}
                            </div>
                            <div class="col-sm-4">
                                ${status}
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- /panel -->

    	</div>
    </jsp:body>    
</t:page>