<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>
	<jsp:attribute name="title">
    	Iteration #${iteration.number()} <small>Project ${project.name()}</small>
    </jsp:attribute>
    
    <jsp:attribute name="actions">
		<a href="${pageContext.request.contextPath}/project/${project.code()}/iteration/${iteration.iterationId()}/story/new" class="btn btn-primary btn-sm">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> New story
	 	</a>
     </jsp:attribute>

     <jsp:body>
     <div class="row">    
		<div class="col-sm-4">
			<t:backlogItemContainer items="${backlog}"></t:backlogItemContainer>
		</div>
		<div class="col-sm-8">
	     	<c:choose>
			    <c:when test="${stories.isEmpty()}">
			    	<div class="panel panel-default">
			    		<div class="panel-heading">
							<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> This iteration doesn't have any story so far
						</div>
					</div>
			    </c:when>    
			    <c:otherwise>
			    	<div class="panel-group" id="story-list" role="tablist" aria-multiselectable="true">
						<c:forEach items="${stories}" var="story">
								<t:storyPanel project="${project}" iteration="${iteration}" story="${story.key}" tasks="${story.value}"/>
						</c:forEach>
					</div>	
			    </c:otherwise>
			</c:choose>
		</div>
	</div> 
    </jsp:body>    
</t:page>