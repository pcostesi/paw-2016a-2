<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Project ${project.name}
    </jsp:attribute>    
    <jsp:attribute name="actions">
	    <a href="/project/${project.code}/iteration/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New iteration
		</a>
		<a href="/project/${project.code}/edit" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit project
		</a>
		<form style="display:inline;" action="/project/${project.code}/delete" method="POST" >
			<button type="submit" class="btn btn-sm btn-danger">
				<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete project
			</button>
		</form>
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