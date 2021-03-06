<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="panelId" required="true"%>
<%@attribute name="title" fragment="true" required="true"%>
<%@attribute name="titleInfo" fragment="true" required="false"%>
<%@attribute name="actions" fragment="true" required="false"%>
<%@attribute name="status" fragment="true" required="false"%>
<%@attribute name="panelClass" required="false"%>
<%@attribute name="panelParent" required="false"%>
<%@attribute name="list" fragment="true" required="false"%>

<jsp:doBody var="bodyText" />

<div class="panel panel-default">
    <div class="panel-heading" role="tab" id="panel-heading-${panelId}">
    	<div class="title-info pull-left">
			<jsp:invoke fragment="titleInfo"/>
		</div> 
    	<div class="extra-left-margin pull-right">
			<jsp:invoke fragment="actions"/>
		</div>  
        <h2 class="panel-title clearfix" title="<jsp:invoke fragment="title"/>">
            <a role="button" data-toggle="collapse" data-parent="${panelParent}" href="#panel-collapse-${panelId}" aria-expanded="true" aria-controls="panel-collapse-${panelId}">
                <jsp:invoke fragment="title"/>
            </a>			          
        </h2>
    </div>
    <div id="panel-collapse-${panelId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel-heading-${panelId}">
        <c:if test="${not empty bodyText}">
        <div class="panel-body">
            <jsp:doBody/>
        </div>
        </c:if>
        <c:if test="${not empty list}">
	        <ul class="list-group">
	            <jsp:invoke fragment="list"/>
	        </ul>
        </c:if>
    </div>
</div>