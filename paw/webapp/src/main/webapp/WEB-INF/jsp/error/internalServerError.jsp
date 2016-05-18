<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:page>

	<jsp:attribute name="title">
    	<spring:message code="error.500.title"/>
    </jsp:attribute>    
        
     <jsp:body>
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-alert" aria-hidden="true"></span> <spring:message code="error.500.reason" arguments="${exception.getMessage()}"/>
				</div>			
			</div>
        </div>
    </jsp:body>
        
</t:page>
