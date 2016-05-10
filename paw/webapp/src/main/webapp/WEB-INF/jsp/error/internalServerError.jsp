<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>

	<jsp:attribute name="title">
    	Well, this is embarrassing
    </jsp:attribute>    
        
     <jsp:body>
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-alert" aria-hidden="true"></span> <b>Looks like our server is having some issues</b><br>${exception.getMessage()}
				</div>			
			</div>
        </div>
    </jsp:body>
        
</t:page>
