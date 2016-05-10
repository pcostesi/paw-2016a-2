<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="item" required="true" type="ar.edu.itba.models.BacklogItem"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="item-${item.backlogItemId()}" panelParent="${panelParent}">
	<jsp:attribute name="title">
		${item.title()}
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<t:deleteButton url="${pageContext.request.contextPath}/project/${project.code()}/backlog/${item.backlogItemId()}"/>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-12">
				<c:choose>
				    <c:when test="${item.description().isPresent()}">
				    	<b>Description</b> ${item.description().get()}<br>
				    </c:when>    
				    <c:otherwise>
				    	<b>Description</b> Item doesn't have a description<br>
				    </c:otherwise>
				</c:choose>
            </div>
        </div>
	</jsp:body>
	
</t:collapsiblePanel>