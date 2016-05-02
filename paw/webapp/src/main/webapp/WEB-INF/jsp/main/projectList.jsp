<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Available Projects
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="/project/${project.code}" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New project
	 	</a>
     </jsp:attribute>
        
        
     <jsp:body>
		<div class="panel-group" id="project-list">
			<c:forEach items="${projects}" var="project">
					<t:projectPanel project="${project}" />
			</c:forEach>
        </div>

    </jsp:body>    
</t:page>
