<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@tag description="NavBar Button" pageEncoding="UTF-8"%>
<%@attribute name="user" required="true" type="ar.edu.itba.models.User" %>
<%@attribute name="admin" required="true" type="ar.edu.itba.models.User" %>
<%@attribute name="members" required="true" type="java.util.List" %>
<%@attribute name="url" required="true" type="java.lang.String" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="row">
	<div class="col-sm-12">
	<p><strong>${admin.username()}</strong> <span class="label label-default">ADMIN</span></p>
	<p><a href="mailto:${admin.mail()}">${admin.mail()}</a></p>
	</div>
</div>
<c:forEach items="${members}" var="member">
	<c:if test="${member ne admin}">
		<hr class="smaller-margins">				
		<div class="row">	
			<div class="col-sm-8">
				<p><strong>${member.username()}</strong></p>
				<p><a href="mailto:${member.mail()}">${member.mail()}</a></p>
			</div>
			<div class="col-sm-4">
				<div class="pull-right">
					<c:if test="${user eq admin}">
						<t:deleteButton url="${url}/${member.username()}"/>
					</c:if>
				</div>
			</div>
		</div>				
	</c:if>						
</c:forEach>