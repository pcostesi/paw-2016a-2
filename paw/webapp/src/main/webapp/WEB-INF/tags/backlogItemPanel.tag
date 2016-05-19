<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="item" required="true" type="ar.edu.itba.models.BacklogItem"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project"%>
<%@attribute name="panelParent" required="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:url value="/project/${project.code()}/backlog/${item.backlogItemId()}" var="deleteUrl" />

<t:collapsiblePanel panelId="item-${item.backlogItemId()}" panelParent="${panelParent}">
	<jsp:attribute name="title">
		${item.title()}
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<t:deleteButton url="${deleteUrl}"/>
	</jsp:attribute>

	<jsp:body>
		<div class="row">
            <div class="col-sm-12">
				<c:choose>
				    <c:when test="${item.description().isPresent()}">
				    	<p><strong><spring:message code="backlogItemPanel.description"/></strong> ${item.description().get()}<p>
				    </c:when>    
				    <c:otherwise>
				    	<p><strong><spring:message code="backlogItemPanel.description"/></strong> <spring:message code="backlogItemPanel.empty"/></p>
				    </c:otherwise>
				</c:choose>
            </div>
        </div>
	</jsp:body>
	
</t:collapsiblePanel>