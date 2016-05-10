<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="items" required="true" type="java.util.List"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:collapsiblePanel panelId="backlog">
	<jsp:attribute name="title">
		Project backlog
	</jsp:attribute>
	
	<jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/${project.code()}/backlog/new" class="btn btn-primary btn-xs dropdown-toggle">
		    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New item
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
				    This backlog doesn't have any items so far
            </div>
        </div>
	</c:if></jsp:body>
	
</t:collapsiblePanel>