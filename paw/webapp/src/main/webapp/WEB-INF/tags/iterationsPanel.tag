<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project" %>
<%@attribute name="iterations" required="true" type="java.util.List" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:forEach items="${iterations}" var="iteration">
	<t:collapsiblePanel panelId="iteration-${iteration.number}">
		<jsp:attribute name="title">Iteration #${iteration.number}</jsp:attribute>	
			<jsp:attribute name="actions">
				<form class="form-inline" action="/project/${project.code}/iteration/${iteration.number}" method="GET" >
					<div class="form-group">
						<button type="submit" class="btn btn-default btn-xs">
						<span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> Go to iteration
						</button>
					</div>
				</form>
				<form class="form-inline" action="/project/${project.code}/iteration/${iteration.number}/delete" method="POST" >
					<div class="form-group">
					<button type="submit" class="btn btn-xs btn-danger">
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
					</button>
					</div>
				</form>
			</jsp:attribute>
			<jsp:body>
				<div class="row">
			        <div class="col-sm-12">
			            <b>Start date</b> ${iteration.beginDate}<br>
			            <b>End date</b> ${iteration.endDate}<br>
			        </div>
			    </div>
			</jsp:body>
	</t:collapsiblePanel>
</c:forEach>
