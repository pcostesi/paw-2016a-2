<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="project" required="true" type="ar.edu.itba.models.Project" %>
<%@attribute name="iterations" required="true" type="java.util.List" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:staticPanel panelId="iterations">
	<jsp:attribute name="title">
		Iterations
	</jsp:attribute>

	<jsp:attribute name="list">
	<c:forEach items="${iterations}" var="iteration">
		<li class="list-group-item">
			<t:iterationPanel iteration="${iteration}" project="${project}" panelParent="#panel-iterations-list" />
		</li>
	</c:forEach>
	</jsp:attribute>
	
	
	<jsp:body>
		<p><strong>Start date</strong> ${project.startDate()}</p>
	    <p><strong>Description</strong> ${project.description()}</p>
	</jsp:body>

</t:staticPanel>
