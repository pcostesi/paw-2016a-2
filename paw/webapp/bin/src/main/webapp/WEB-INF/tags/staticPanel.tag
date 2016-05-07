<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="panelId" required="true"%>
<%@attribute name="title" fragment="true" required="true"%>
<%@attribute name="actions" fragment="true" required="false"%>

<div class="panel panel-default" id="${panelId}">
    <div class="panel-heading" role="tab">
        <h2 class="panel-title">
            <jsp:invoke fragment="title"/>
            <div class="pull-right">
			  	<jsp:invoke fragment="actions"/>
			</div>
        </h2>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-12">
				<jsp:doBody/>
			</div>
		</div>
	</div>
</div>