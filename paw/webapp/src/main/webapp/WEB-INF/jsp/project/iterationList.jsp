<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Project ${project.name()}
    </jsp:attribute>
     
    <jsp:attribute name="actions">
	    <a href="${pageContext.request.contextPath}/project/${project.code()}/iteration/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New iteration
		</a>
     </jsp:attribute>
     
     <jsp:body>
		<div class="row">
			<div class="col-md-6 col-sm-12">
				<t:iterationsPanel project="${project}" iterations="${iterations}"/>
			</div>
			<div class="col-md-6 col-sm-12">
				<t:backlogPanel project="${project}" backlog="${backlog}"/>
			</div>
		</div>
    </jsp:body>    
</t:page>    
</body>
</html>