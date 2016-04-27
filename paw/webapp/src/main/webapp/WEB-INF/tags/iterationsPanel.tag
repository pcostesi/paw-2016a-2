<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="iterations" required="true" type="java.util.List" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<div class="panel panel-default">
    <div class="panel-heading" role="tab">
        <h2 class="panel-title">
            Iterations
        </h2>
    </div>
    <div class="panel-collapse collapse in" role="tabpanel">	
		<ul class="list-group">
			<c:forEach items="${iterations}" var="iteration">
				<li class="list-group-item">
					<a href="/project/scrumlr/iteration/${iteration.iterationId}">Iteration #${iteration.iterationId}</a>
				</li>
			</c:forEach>
		</ul>
    </div>
</div> <!-- /panel -->
