<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="panelId" required="true"%>
<%@attribute name="title" fragment="true" required="true"%>
<%@attribute name="actions" fragment="true" required="false"%>
<%@attribute name="panelClass" required="false"%>
<%@attribute name="panelParent" required="false"%>

<div class="panel panel-default">
    <div class="panel-heading" role="tab" id="panel-heading-${panelId}">
        <h2 class="panel-title clearfix">
            <a role="button" data-toggle="collapse" data-parent="${panelParent}" href="#panel-collapse-${panelId}" aria-expanded="true" aria-controls="panel-collapse-${panelId}">
                <jsp:invoke fragment="title"/>
            </a>
			<div class="pull-right">
			  	<jsp:invoke fragment="actions"/>
			</div>            
        </h2>
    </div>
    <div id="panel-collapse-${panelId}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="panel-heading-${panelId}">
        <div class="panel-body">
            <jsp:doBody/>
        </div>
    </div>
</div> <!-- /panel -->