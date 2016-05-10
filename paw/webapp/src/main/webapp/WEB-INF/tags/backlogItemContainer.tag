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

	<jsp:body>
		<div class="row">
            <div class="col-sm-12">
				<c:choose>
				    <c:when test="${items.isEmpty()}">
				    	This backlog doesn't have any items so far
				    </c:when>    
				    <c:otherwise>
				    	<div class="panel-group" id="backlog-group" role="tablist" aria-multiselectable="true">
					    	<c:forEach items="${items}" var="backlogItem">
					    		<t:backlogItemPanel item="${backlogItem}" panelParent="#backlog-group"/>
					    	</c:forEach>	
				    	</div>
				    </c:otherwise>
				</c:choose>
            </div>
        </div>
	</jsp:body>
</t:collapsiblePanel>