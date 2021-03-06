<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="panelId" required="true"%>
<%@attribute name="title" fragment="true" required="true"%>
<%@attribute name="actions" fragment="true" required="false"%>
<%@attribute name="titleInfo" fragment="true" required="false"%>
<%@attribute name="list" fragment="true" required="false"%>

<jsp:doBody var="bodyText" />

<div class="panel panel-default" id="${panelId}">
    <div class="panel-heading" role="tab">
    	<div class="title-info pull-left">
			<jsp:invoke fragment="titleInfo"/>
		</div> 
	    <div class="extra-left-margin pull-right">
		  	<jsp:invoke fragment="actions"/>
		</div>
        <h2 class="panel-title" title="<jsp:invoke fragment="title"/>">
            <jsp:invoke fragment="title"/>            
        </h2>
    </div>
    
       <c:if test="${not empty bodyText}">
        <div class="panel-body">
            ${bodyText}
        </div>
    </c:if>
    <c:if test="${not empty list}">
	    <ul class="list-group">
	        <jsp:invoke fragment="list"/>
	    </ul>
   	</c:if>
</div>