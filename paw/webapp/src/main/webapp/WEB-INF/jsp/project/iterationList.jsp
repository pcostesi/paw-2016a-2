<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Project Scrumlr
    </jsp:attribute>
    
    <jsp:attribute name="actions">
	  <t:navbutton btnClass="btn-danger">
		Delete
	  </t:navbutton>
	  <t:navbutton btnClass="btn-default">
	    Edit
	  </t:navbutton>
	  <t:navbutton btnClass="btn-primary">
	    New Iteration
	  </t:navbutton>
     </jsp:attribute>
        
        
     <jsp:body>
<div class="panel-group" aria-multiselectable="true">


			<div class="panel panel-default">
                <div class="panel-heading" role="tab">
                    <h2 class="panel-title">
                        Project Scrumlr Highlights
                    </h2>
                </div>
                <div class="panel-collapse collapse in" role="tabpanel">

                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                            No users currently owning this project.
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- /panel -->
            
			<div class="panel panel-default">
                <div class="panel-heading" role="tab">
                    <h2 class="panel-title">
                        Iterations
                    </h2>
                </div>
                <div class="panel-collapse collapse in" role="tabpanel">

                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                            	<ul>
                            		<li><a href="/project/scrumlr/iteration/1">Iteration #1</a></li>
                            	</ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- /panel -->

        </div>

    </jsp:body>    
</t:page>
    
</body>

</html>