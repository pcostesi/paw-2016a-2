<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project" %>
<%@attribute name="item" required="true" type="ar.edu.itba.models.BacklogItem" %>
<%@attribute name="panelParent" required="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:url value="/project/${project.code()}/backlog/new" var="newUrl" />

<t:collapsiblePanel panelId="backlog-item-${item.backlogItemID()}" panelParent="${panelParent}">

	<jsp:attribute name="title">
		${item.name()}
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<a class="btn btn-default btn-xs" href="${newUrl}">Storify</a>
		<a class="btn btn-default btn-xs" href="${newUrl}">Taskify</a>
		<a class="btn btn-danger btn-xs" href="${newUrl}">Remove</a>
	</jsp:attribute>

	<jsp:body>
		<p>${item.description()}</p>
	</jsp:body>
	
	
</t:collapsiblePanel>