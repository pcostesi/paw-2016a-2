<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Project ${project.name}
    </jsp:attribute>
     
    <jsp:attribute name="actions">
	    <a href="${pageContext.servletContext}/project/${project.code}/iteration/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New iteration
		</a>
     </jsp:attribute>
     
     <jsp:body>
			<div class="panel-group" aria-multiselectable="true">
				<t:descriptionPanel project="${project}"/>
				<t:iterationsPanel project="${project}" iterations="${iterations}"/>
			</div>
    </jsp:body>    
</t:page>    
</body>
</html>