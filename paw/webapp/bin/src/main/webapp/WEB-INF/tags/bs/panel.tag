<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="Bootstrap Panel" pageEncoding="UTF-8"%>
<%@attribute name="panelId" required="true"%>
<%@attribute name="title" fragment="true" required="true"%>
<%@attribute name="actions" fragment="true" required="false"%>
<%@attribute name="panelClass" required="false"%>

<div class="panel panel-default">
    <div class="panel-heading" id="panel-heading-${panelId}">
        <h2 class="panel-title clearfix">
            <jsp:invoke fragment="title"/>

			<div class="btn-group pull-right">
			  	<jsp:invoke fragment="actions"/>
			</div>
            
        </h2>
    </div>

    <div class="panel-body">
        <jsp:doBody/>
    </div>
</div> <!-- /panel -->