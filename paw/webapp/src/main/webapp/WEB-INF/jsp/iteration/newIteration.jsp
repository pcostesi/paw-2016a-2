<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:page>
	<jsp:attribute name="title">
    	Add a new iteration <small>for Project Scrumlr</small>
	</jsp:attribute>
	
	<jsp:body>
		<form action="/project/scrumlr/iteration" method="POST">
			<div class="row">
				<div class="col-sm-12">
					<p>Adding...</p>
				</div>
			</div>
		</form>
    </jsp:body>
</t:page>
