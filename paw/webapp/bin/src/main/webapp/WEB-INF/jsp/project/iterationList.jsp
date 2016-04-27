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
				<t:projectHighlights/>
				<t:iterationsPanel iterations="${iterations}"/>
			</div>

    </jsp:body>    
</t:page>
    
</body>

</html>