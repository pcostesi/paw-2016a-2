<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="items" required="true" type="java.util.List"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url value="/project/${project.code()}/backlog/new" var="newLink"/>

<t:collapsiblePanel panelId="backlog">
	<jsp:attribute name="title">
		<spring:message code="backlogItemContainer.title"/>
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<a href="${newLink}" class="btn btn-primary btn-xs dropdown-toggle">
		    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> <spring:message code="backlogItemContainer.new_item"/>
		</a>
	</jsp:attribute>
	
	<jsp:attribute name="list"><c:if test="${not items.isEmpty()}">		
		<c:forEach items="${backlog}" var="item">
			<li class="list-group-item">
				<t:backlogItemPanel project="${project}" item="${item}"/>
			</li>
		</c:forEach>  		
	</c:if></jsp:attribute>

	<jsp:body><c:if test="${items.isEmpty()}">
		<div class="row">
            <div class="col-sm-12">
				<p><spring:message code="backlogItemContainer.empty_list"/></p>
            </div>
        </div>
	</c:if></jsp:body>
	
</t:collapsiblePanel>